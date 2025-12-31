# `DispatcherServlet` 뜯어보기

`comment`
> 이 메서드는 실제로 요청을 핸들러에게 전달하는 역할을 한다.먼저 등록된 HandlerMapping들을 순서대로 적용해 핸들러를 찾고,
그 핸들러를 실행할 수 있는 HandlerAdapter를 선택한다.모든 HTTP 메서드는 이 메서드를 통해 처리되며,
어떤 메서드가 허용되는지는 HandlerAdapter나 핸들러가 판단한다.

```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
  HttpServletRequest processedRequest = request;
  HandlerExecutionChain mappedHandler = null;
  boolean multipartRequestParsed = false;

  WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

  try {
    ModelAndView mv = null;
    Exception dispatchException = null;

    try {
      processedRequest = checkMultipart(request);
      multipartRequestParsed = (processedRequest != request);

      // Determine handler for the current request.
      mappedHandler = getHandler(processedRequest);
      if (mappedHandler == null) {
        noHandlerFound(processedRequest, response);
        return;
      }

      if (!mappedHandler.applyPreHandle(processedRequest, response)) {
        return;
      }

      // Determine handler adapter and invoke the handler.
      HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
      mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

      if (asyncManager.isConcurrentHandlingStarted()) {
        return;
      }

      applyDefaultViewName(processedRequest, mv);
      mappedHandler.applyPostHandle(processedRequest, response, mv);
    }
    catch (Exception ex) {
      dispatchException = ex;
    }
    catch (Throwable err) {
      // As of 4.3, we're processing Errors thrown from handler methods as well,
      // making them available for @ExceptionHandler methods and other scenarios.
      dispatchException = new ServletException("Handler dispatch failed: " + err, err);
    }
    processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
  }
  catch (Exception ex) {
    triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
  }
  catch (Throwable err) {
    triggerAfterCompletion(processedRequest, response, mappedHandler,
        new ServletException("Handler processing failed: " + err, err));
  }
  finally {
    if (asyncManager.isConcurrentHandlingStarted()) {
      // Instead of postHandle and afterCompletion
      if (mappedHandler != null) {
        mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
      }
      asyncManager.setMultipartRequestParsed(multipartRequestParsed);
    }
    else {
      // Clean up any resources used by a multipart request.
      if (multipartRequestParsed || asyncManager.isMultipartRequestParsed()) {
        cleanupMultipart(processedRequest);
      }
    }
  }
}
```

## `checkMultipart(request)`
`comment`
> 요청을 멀티파트 요청으로 변환하고, 멀티파트 리졸버를 사용할 수 있게 한다.
만약 멀티파트 리졸버가 설정되어 있지 않다면, 기존 요청을 그대로 사용한다.
참고: MultipartResolver.resolveMultipart

- 멀티파트인지 아닌지 확인해보고 맞으면 반환, 아니면 멀티파트 전용 Request 래퍼로 감싸서 반환

```java
protected HttpServletRequest checkMultipart(HttpServletRequest request) throws MultipartException {
  if (this.multipartResolver != null && this.multipartResolver.isMultipart(request)) {
    if (WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class) != null) {
      if (DispatcherType.REQUEST.equals(request.getDispatcherType())) {
        logger.trace("Request already resolved to MultipartHttpServletRequest, for example, by MultipartFilter");
      }
    }
    else if (hasMultipartException(request)) {
      logger.debug("Multipart resolution previously failed for current request - " +
          "skipping re-resolution for undisturbed error rendering");
    }
    else {
      try {
        return this.multipartResolver.resolveMultipart(request);
      }
      catch (MultipartException ex) {
        if (request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) != null) {
          logger.debug("Multipart resolution failed for error dispatch", ex);
          // Keep processing error dispatch with regular request handle below
        }
        else {
          throw ex;
        }
      }
    }
  }
  // If not returned before: return original request.
  return request;
}
```

## `getHandler(processedRequest)`
`comment`
> 모든 HandlerMapping들을 순서대로 시도해서 HandlerExecutionChain 또는 핸들러를 찾을 수 없는 경우 null을 반환

`handler mapping`
> HandlerMapping은 요청과 핸들러를 연결하는 전략 인터페이스로, DispatcherServlet이 우선순위대로 이를 순회해 매칭되는 핸들러를 찾고 인터셉터와 함께 HandlerExecutionChain으로 감싸 실행 전·후 흐름을 제어한다.
이 구조는 세션·쿠키 등 다양한 조건 기반 매핑을 가능하게 하는 Spring MVC의 높은 유연성을 제공한다.

```java
protected @Nullable HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
  if (this.handlerMappings != null) {
    for (HandlerMapping mapping : this.handlerMappings) {
      HandlerExecutionChain handler = mapping.getHandler(request);
      if (handler != null) {
        return handler;
      }
    }
  }
  return null;
}
```

1. `DispatcherServlet`이 초기화될 때(서버 부팅 시) `initHandlerMappings()` 같은 초기화 로직을 돌리면서 ApplicationContext내에 타입이 HandlerMapping인 빈들을 전부 찾아 handlerMappings 리스트에 모아 넣고 Ordered/@Order 기준으로 정렬하면 `RequestMappingHandlerMapping` 같은게 리스트 맨 위에 있게됨
  - 이와 별도로, RequestMappingHandlerMapping 자체도 초기화 과정에서 @Controller / @RestController 빈들을 스캔하고, 각 컨트롤러의 @RequestMapping / @GetMapping / @PostMapping 메서드를 분석해 “요청 조건(RequestMappingInfo) → 실행할 컨트롤러 메서드(HandlerMethod)” 를 자기 내부의 매핑 테이블(Map)로 미리 만들어 저장해 둔다.
2. `DispatcherServlet`이 HandlerMapping 리스트를 순회해서 잘 맞는 핸들러를 매핑
  - 현재 요청(URL, HTTP 메서드, 조건 등)과 가장 잘 맞는 HandlerMethod(= 컨트롤러 객체 + 메서드 정보) 를 찾아낸다.
3. `HandlerExecutionChain` 타입으로 가져옴
   * HandlerExecutionChain 내부에는 handler, interceptor의 리스트가 있다.


## `getHandlerAdapter(mappedHandler.getHandler())`
- applyPreHandle 이후에 문제가 없으면 getHandlerAdapter를 실행함
- DispatcherServlet는 어떤 컨트롤러(핸들러)든 실행만 할 수 있으면 되니까 `mappedHandler.getHandler()`처럼 구체적인 핸들러까지 알 필요가 없기 때문에 변수로 받지 않았음
  - 핸들러를 실행 가능한 어댑터만 찾으면 됨

```java
protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
  if (this.handlerAdapters != null) {
    for (HandlerAdapter adapter : this.handlerAdapters) {
      if (adapter.supports(handler)) {
        return adapter;
      }
    }
  }
  throw new ServletException("No adapter for handler [" + handler +
      "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
}
```

1. DispatcherServlet 초기화 과정에서 `initHandlerAdapters()` 같은 초기화 로직을 돌리면서 ApplicationContext에서 타입이 HandlerAdapter인 빈을 전부 조회 List<HandlerAdapter> handlerAdapters에 저장후 Ordered / @Order 기준으로 정렬
   - 보통 `RequestMappingHandlerAdapter`
   - 우선 순위는 별도고, 결정은 `supports()`가 함

```java
public boolean supports(Object handler) {
    return (handler instanceof HandlerMethod);
}
```

2. 이 핸들러가 HandlerMethod가 True면 해당 어댑터가 리턴됨

## `ha.handle(processedRequest, response, mappedHandler.getHandler());`
- handle을 통해 어댑터를 실행함
- mv는 `ModelAndView`클래스로 선언되었는데 Model은 컨트롤러 실행 결과 데이터, View는 어떤 화면(뷰)로 보여줄지의 결과가 담겨있다.

## `mappedHandler.applyPostHandle(processedRequest, response, mv);`
- applyPostHandle()은 컨트롤러(핸들러)가 정상 실행된 직후, 뷰가 렌더링되기 전에, 등록된 인터셉터들의 postHandle()을 역순으로 호출하는 코드
  - preHandle에서 순서대로 쌓인 컨텍스트를 안전하게 되돌리기 위한 스택 구조라서 그럼
  - 여러 페이지에서 공통으로 필요한 데이터를 ModelAndView에 보완하기 위한 단계다.
- Spring MVC에서는 뷰 렌더링에 필요한 공통 데이터를 컨트롤러 실행 이후, 화면이 만들어지기 직전 단계(postHandle)에서 ModelAndView에 가공·주입함으로써 중복 코드를 제거한다.

```java
void applyPostHandle(HttpServletRequest request, HttpServletResponse response, @Nullable ModelAndView mv)
    throws Exception {

  for (int i = this.interceptorList.size() - 1; i >= 0; i--) {
    HandlerInterceptor interceptor = this.interceptorList.get(i);
    interceptor.postHandle(request, response, this.handler, mv);
  }
```


## 그려보기
<img width="1138" height="960" alt="image" src="https://github.com/user-attachments/assets/393222a2-dc8d-4006-86e0-4c36b6711211" />
