#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 클래스 로더 생성 후 클래스 로드
* ServletContext 생성
* DispatcherServlet init() 호출(WebServlet loadOnStartup 속성이 1 이므로)
* RequestMapping의 initMapping() 호출을 통해 매핑

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* URL을 *.next로 가진 DispatcherServlet의 서블릿 스레드 생성
* service 메소드 호출
* RequestMapping에 url을 가지고 mapping되어있던 컨트롤러 오브젝트를 실행하여 jsp파일을 render해준다. 

#### 8. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 상태 값을 저장하는 필드를 클래스 필드로 두면 모든 쓰레드에서 하나의 필드를 공유하게 된다.
* 각 쓰레드마다 상태를 가지고 있어야 하므로 지역변수로 선언 해 주면 된다. 

