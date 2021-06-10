# spring jpa를 이용하여 간단한 게시판 구현<br>
그렇기 때문에 css등의 디자인적 요소는 신경쓰지 않았습니다.

# 게시물을 이용한 등록, 수정, 삭제, 조회 기능<br>
SPRING JPA를 이용한 데이터 등록, 수정, 삭제, 조회를 구현

# 2021/05/27<br>
## 1. Controller<br><br>
@Controller<br>
컨트롤러임을 명시하는 어노테이션입니다.<br>
MVC에서 컨트롤러로 명시된 클래스의 메서드들은 반환 값으로 템플릿 경로를 설정하거나, redirect를 해줘야 합니다.<br>
템플릿 경로는 templates 패키지를 기준으로한 상대 경로입니다.<br><br>
@AllArgsConstructor<br>
스프링 프레임 워크에서는 보통 아래와 같은 3가지 방법으로 Bean을 주입합니다.<br>
@Autowired<br>
setter<br>
생성자<br>
위 어노테이션은 생성자를 자동으로 생성해줍니다.<br>
이 방법으로 BoardService 객체를 주입받을 수 있습니다.<br><br>
@GetMapping/@PostMapping/@PutMapping/@DeleteMapping<br>
URL을 매핑해주는 어노테이션입니다.<br>
HTTP Method에 맞는 어노테이션을 작성하면 됩니다.
   

## 2. Service<br><br>
@Service<br>
   서비스 계층인 것을 명시하는 어노테이션입니다.<br><br>
   @AllArgsConstructor<br>
   위 Controller에서 사용했듯이 여기선 Repository 객체를 주입합니다.<br><br>
   @Transactional<br>
   선언적 트랜잭션이라고 합니다.<br>
   트랜잭션을 적용하는 어노테이션입니다.<br>
   선언된 메서드를 진행하는 동안 원자성, 일관성, 격리성, 지속성을 유지할 수록 합니다.
  
 
## 3. Repository<br><br>
인터페이스를 정의하고 JpaRepository 인터페이스를 상속받게 됩니다.<br>
   이 과정에서 Entity와 PK의 타입을 명시하게 됩니다.<br><br>
   위 Service에서 findById(), deleteById()등이 사용되었으나 repository에서 이를 확인할 수 없습니다<br>
   위 메서드들은 JpaRepository에 정의되어 있습니다.
   

## 4. Entity<br><br>
@NoArgsConstructor(access=AccessLevel.PROTECTED)<br>
   파라미터가 없는 기본 생성자를 생성하는 어노테이션입니다.<br>
   JPA를 사용하기 위해서는 기본 생성자가 필수입니다.<br>
   access는 생성자의 접근 권한을 설정하는 속성입니다. 최종적으로 protected BoardEntity(){}와 동일합니다.<br>
   entity를 외부에서 생성할 필요가 없기 때문에 protected로 생성합니다.<br><br>
   @Getter<br>
   모든 필드에서 getter를 자동으로 생성해주는 어노테이션입니다.<br><br>
   @Entity<br>
   객체를 테이블에 매핑할 때 사용하는 어노테이션입니다.<br>
   이 어노테이션을 사용한 클래스는 JPA가 관리하게 되며 Entity클래스라고 합니다.<br><br>
   @Table<br>
   Entity클래스와 매핑되는 Table의 정보를 명시하는 어노테이션입니다.<br>
   name 속성으로 테이블 명을 작성할 수 있습니다.<br>
   생략될 경우 Entity 이름으로 자동으로 테이블에 매핑됩니다.<br><br>
   @Id<br>
   테이블의 기본키를 명시하는 어노테이션입니다.<br><br>
   @GeneratedValue<br>
   기본키를 자동으로 생성하기 위해 사용하며 IDENTITY는 MySQL을 사용하기 때문에 명시되었습니다.<br><br>
   @Column<br>
   칼럼을 매핑하는 어노테이션입니다.<br><br>
   @Builder<br>
   빌더패턴 클래스를 생성해주는 어노테이션입니다.<br>
   setter를 사용할 경우 안정성을 보장받을 수 없기 때문에 빌더패턴을 사용합니다.
   

## 5. TimeEntity<br><br>
@MappedSuperClass<br>
   테이블을 매핑하지 않고 자식 클래스에게 매핑정보를 상속하기 위한 어노테이션입니다.<br><br>
   @EntityListeners(AuditingEntityListener.class)<br>
   JPA에게 해당 Entity는 Auditing기능을 사용한다고 알립니다.<br><br>
   @CreatedDate<br>
   Entity가 처음 저장될 때 생성일을 주입하는 어노테이션입니다.<br>
   생성일은 수정될 필요가 없기 때문에 updatable=false속성을 추가합니다.<br>
   속성을 추가하지 않을 시에는 해당 값이 null이 됩니다.<br><br>
   @LastModifiedDate<br>
   Entity가 수정될 때 수정 일자를 주입하는 어노테이션입니다.
   

## 6. @EnableJpaAuditing<br>
JPA Auditing을 활성화 시키기 위해서는 Application에서 @EnableJpaAuditing 어노테이션을 추가해야 합니다.
   

## 7. 이후 구현 방향성<br>
게시판에 필요한 다른 entity(ex. 회원, 댓글 등) 구현
추가될 entity에 맞춰 필요한 기능들 추가
   

# 2021/05/28<br>
## 1. 댓글 entity 추가<br>
댓글 entity가 추가됨에 따라 필요한 dto, repository, service 등이 추가되었습니다.

## 2. @modify 사용<br>
앞서 사용했던 JPA repository를 사용하여 삭제를 진행할 수 있지만 그렇게 될 경우 내부적으로 조회(select) 이후 삭제(delete)가 이루어진다는 정보를 얻었다.<br>
댓글이 많아진 게시물이라면 문제가 성능에 문제가 생길 수 있기 때문에 @modify를 사용해 delete과정만을 진행합니다.
   
## 2. 기존 오류 수정<br>
게시글 수정의 오류 수정 (/board/update.html에 있던 문제점 수정)
   
## 3. 이후 구현 방향성<br>
페이징 추가<br>
   유저 혹은 멤버를 이용한 로그인 기능 추가<br>
   controller 완전 분리<br>
   (현재 게시물의 세부사항 조회와 댓글 리스트 조회를 한번에 호출해야 합니다.)<br>
   (또한 게시물 삭제 시에 해당하는 댓글을 한번에 삭제해야 합니다.)<br>
   (위 두가지 상황때문에 controller를 완벽하게 분리하지 못했습니다.)

# 2021/05/29<br>
## 1. controller 분리 관련 결론<br>
entity만을 기준으로 controller를 분리하려 했던거이 문제였습니다.<br>
앞으로의 기준을 좀 더 사용자의 잎장에 맞추도록 하겠습니다.<br>
게시글 등록, 수정, 삭제, 조회 등은 확실히 게시글에 초점을 두고 있기 때문에 이는 하나의 controller로 분류하는 것이 좋다고 생각합니다.<br>
게시글 조회의 경우 댓글의 조회역시 동시에 일어나기 때문에 boardController에서 conmmentService를 이용하게 됩니다.<br>
이를 완전히 분리하기 위해서는 게시글 조회 시에 서로 다른 GetMapping 2개를 호출하게 됩니다.<br>
이 과정이 올바른 것인지도 확신할 수 없기 때문에 확실한 공부 이후 언급하겠습니다.

## 2. 회원 추가<br>
Spring Security를 이용하여 로그인 및 회원 기능을 수행해 보도록 하겠습니다.

# 2021/06/02<br>
이번엔 Spring Security를 이용해서 회원 로그인을 구현해 보았습니다.<br>
Spring Security란?<br>
보안 솔루션을 제공해주는 Spring 기반의 스프링 하위 프레임워크입니다.<br>
보안 솔루션이 제공되기 때문에 보안 관련 로직을 짤 필요가 없어집니다.<br>

## 1. SecurityConfig<br>
Spring Security에 관련된 설정을 하기 위해서 설정 파일을 만들어야 합니다.<br>
설정 파일은  WebSecurityConfigurerAdapter를 상속받아 구현합니다.<br><br>

@EnableWebSecurity<br>
Spring Security를 활성화 한다는 의미를 지닌 어노테이션입니다.<br><br>

WebSecurityConfigurerAdapter<br>
설정을 하기 위해 상속해야 하는 클래스입니다.<br><br>

passwordEncorder()<br>
BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체입니다.<br>
service에서 비밀번호를 암호화할 수 있도록 Bean으로 등록합니다.<br><br>

configure(WebSecurity web)<br>
WebSecurity는 FilterChainProxy를 생성하는 필터입니다.<br>
web.ignoring().antMathchers();<br>
해당 경로의 파일들은 Spring Security가 무시할 수 있도록 설정합니다.<br>
파일 기본 기준은 resources/static 디렉터리입니다.<br><br>

configure(HttpSecurity http)<br>
HTTP요청에 따라 접근을 제한합니다.<br>
authorizeRequests()<br>
메서드로 특정 경로를 지정하며 permitAll(), hasRole() 메서드로 역할(Role)에 따른 접근 설정을 잡아줍니다.<br>
ex) anyRequest().authenticated()<br>
모든 요청에 대해 인증된 사용자만 접근하도록 설정할 수 있습니다.<br><br>

formlogin()<br>
form 기반으로 인증하도록 합니다.<br>
로그인 종보는 기본적으로 HttpSession을 이용합니다.<br>
/login 경로로 접근하며, SpringSecurity에서 제공하는 로그인 form을 사용할 수 있습니다.<br>
loginPage("/login")<br>
기본적으로 제공되는 form이외에 커스텀 로그인 폼을 사용하고 싶다면 loginPage() 메서드를 사용합니다.<br>
이 때 커스텀 로그인 form의 action 경로와 loginPage의 파라미터 경로가 일치해야 인증을 처리할 수 있습니다.<br>
defaultSuccessUrl("/")<br>
로그인 성공시에 이동되는 페이지를 지정합니다.<br>
usernameParameter(""), passwordParameter("")<br>
로그인 form에서 아이디는 name=username인 input을 기본으로 인식합니다.<br>
위 메서드를 통해 파라미터 명을 변경할 수 있습니다.<br><br>

logout()<br>
로그아웃을 지원하는 메서드입니다.<br>
WebSecurotyConfigurerAdapter를 사용할 때 자동적으로 적용됩니다.<br>
기본적으로 "logout"에 접근하면 HTTP세션을 제거합니다.<br>
logoutRequestMatcher(new AntPathRequestMatcher("/logout"))<br>
로그아웃의 기본 URL을 다른 URL로 재정의 합니다.<br>
incalidateHttpSession(true)<br>
HTTP 세션을 초기화하는 작업입니다.<br><br>

exceptionHandling().accessDeniedPage("/denied")<br>
예외가 발생했을 시 exceptionHandling()메서드로 핸들링할 수 있습니다.<br><br>

configure(AuthenticationManageBuilder auth)<br>
Spring Security에서 모든 인증은 AuthenticationManaget를 통해 이루어지며 AuthenticationManager를 생성하기 위해서는 AuthenticationManagerBuilder를 사용합니다.<br>
로그인 처리 즉, 인증을 위해서는 UserDetailService를 통해서 필요한 정보들을 가져와야 합니다.<br>
이를 memberService에서 이를 처리합니다.<br>
서비스 클래스에서 UserDetailService 인터페이스를 상속한 뒤에 loadUserByUsername()메서드를 구현하면 됩니다.<br><br>

## 2. Controller
   로그인, 로그아웃, 접근 거부 등을 다룬 MemberController를 추가했습니다.<br>

UserDetails.getUsername()<br>
게시물, 댓글을 작성할 때 작성자의 이름을 알기 위해 유저 정보를 가져옵니다.<br>
위 Spring Security에서 username을 id로 설정했기 때문에 id에 대한 정보를 가져옵니다.<br><br>

## 3. Service<br>
   UserDetailsService를 상속합니다.<br><br>

joinUser()<br>
회원가입을 처리하는 메서드입니다.<br>
예제의 메서드는 비밀번호를 암호화하여 저장합니다.<br><br>

loadUserByUsername()<br>
상세 정보를 조회하는 메서드입니다.<br>
계정 정보와 권한을 갖는 userDetails 인터페이스를 반환해야 합니다.<br>
매개 변수로서 로그인 시 입력한 아이디를 사용합니다.<br>
기본적인 값으로 username을 사용합니다.<br>
Spring Security 설정 단계에서 이 매개변수의 이름을 변경할 수 있습니다.<br><br>
authorities.add(new SimpleGrantedAuthority());<br>
역할을 부여하는 코드입니다.<br><br>
new User()<br>
UserDetails를 구현한 User를 반환합니다.<br>
Id, Password, 권한 리스트가 담겨 있습니다.<br><br>

check~Duplicate<br>
중복검사를 위해 만든 메서드입니다.<br>
예제에서는 id와 nickname으로 중복여부를 검사하였습니다.<br>

## 4. Role.java<br>
   사용할 역할들을 모아놓은 Enum객체입니다.<br><br>

## 5. Repository<br>
   existsBy~()<br>
   Service에서 중복검사를 진행하기 위해 정의되었습니다.<br><br>

## 6. Entity<br>
   다른 Entity와 차이는 거의 없습니다.<br>
   단 id를 직접 입력하기 때문에 자동생성을 하지 않습니다.<br><br>

## 7. Dto<br>
   @Valid를 이용하여 유효성을 검사합니다.<br>
   각각에서 요구하는 형식을 만족하지 못할 시에 에러메시지를 반환합니다.<br><br>

@NotBlank(message = "")<br>
공백을 허용하지 않습니다.<br><br>
@Email(message = "")<br>
입력값이 이메일 형식을 띄어야 합니다.<br><br>
@Pattern(regexp="", message="")<br>
입력값에 정규식을 적용하여 검사할 수 있습니다.<br><br>

## 8. html<br>
sec:authorize를 사용하여 사용자의 Role에 따라 보이는 메뉴를 다르게 합니다.<br><br>
isAnonymous()<br>
익명의 사용자일 경우 노출됩니다.<br>
isAuthenticated()<br>
인증된 사용자의 경우 노출됩니다.<br>
hasRole()<br>
특정 역할을 가진 사용자에 대해 노출합니다.<br><br>
input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"<br>
form에 히든 타입으로 crsf토큰 값을 넘겨줍니다.<br>
Spring Security가 적용될 경우 POST방식으로 보내는 모든 데이터는 crsf토큰 값이 필요합니다.<br><br>
input type="text" name="id" placeholder="ID를 입력해주세요"<br>
로그인 시 아이디의 name은 username 혹은 Spring Security에서 설정된 값이어야 합니다.<br>
예제의 경우 id로 지정되었습니다.<br>
   
## 9. 이후 구현 방향<br>
 1. 게시물, 댓글의 페이징 및 검색기 능 추가<br>
 2. 게시물, 댓긓의 추천 기능 추가<br>
 3. 게시물, 댓글의 정렬 기능 추가<br><br>

최종적으로 배포를 목표로 하고 있습니다.

# 2021/06/04<br>
## 1. Controller<br>
CommentController에 request mapping에 오류가 있어 이를 수정했습니다.<br>
BoardController의 경우 검색 기능과 페이징 기능의 추가로 인해 몇가지 메소드들이 변경 및 추가되었습니다.<br><br>
@RequestParam(value="", defaultValue="")<br>
단일 파라미터를 전달받을 때 사용하는 어노테이션입니다.<br>
이 어노테이션을 사용하여 검색과 페이징에 필요한 정보(pageNum, keyword)를 전달받습니다.<br>
defaultValue값을 url을 추가할 필요 없이 기존의 boardList를 수정하는 것으로 페이징과 검색을 동시에 진행할 수 있습니다.<br><br>

## 2. Service<br>
getPostList(), getPageList()를 추가했습니다.<br>
Controller에서 언급되었듯이 pageNum, keyword의 초기값이 지정되어있으며 이를 Service로 전달했을 때 두 메서드는 두 값이 지정된 초깃값일때와 아닐 때로 구분하여 다른 과정을 메서드를 수행합니다.<br><br>

## 3. Repository<br>
JpaRepository Syntax의 복잡도가 올라갔습니다.<br>
이번에 사용된 메소드(countByTitleContainingOrContentContaining())의 경우 Or, Containing등의 문법을 추가로 사용하게 되었습니다.<br>
이처럼 JpaRepository의 경우 메서드명의 By 이후는 SQL문의 Where조건절에 해당하는 문법이 나타나게 됩니다.<br>
위 예시로 살펴보면 Title에 포함(Containing)되어 있거나(Or) Content에 포함(Containing)되어 있는 갯수를 구하는 것이 됩니다.<br>
좀 더 많은 문법사용법을 원하신다면 [여기][sql_link] 를 이용하시면 됩니다.

[sql_link]: https://docs.spring.io/spring-data/jpa/docs/1.10.1.RELEASE/reference/html/#jpa.sample-app.finders.strategies

## 4.html<br>
검색 기능은 form을 사용하여 keyword를 전달하는 방식을 채택했습니다.<br>
페이지의 경우 server로 부터 페이징 번호 리스트 pageList변수를 받아와 출력합니다.<br>
또한 각 번호를 누를 경우 해당 페이지 번호를 server로 전달합니다.<br><br>

## 5. 이후 구현 방향<br>
이번에 추가된 검색 기능과 페이징 기능을 작성하는데 있어 Controller와 Service를 기능을 정상적으로 분리하지 못한 것 같습니다.<br>
이후 개발에선 이 부분을 최우선적으로 수정하겟습니다.<br>
1. 추천 기능 추가<br>
2. 검색 기능에 검색 조건 추가<br>
3. 정렬기능 추가<br>

# 2021/06/07<br>
게시물과 댓글에 좋아요와 추천 기능을 추가했습니다.<br>
N:M의 관계를 가지기 때문에 해로운 Entity를 추가했습니다.<br>
(한명 의 멤버가 여러 게시물과 댓글에 좋아요와 추천을 할 수 있습니다, 한 게시물 역시 여러 맴버들에게 좋아요를 받을 수 있습니다.)<br>
이번 과정에서의 JpaRepository를 사용하면서 다른 column으로 다른 entity를 사용하는 과정이 핵심적이었습니다.<br>
JpaRepository에서 entity를 사용하며 DB에서는 어떠한 형태로 저장되는 지 확인할 수 있었습니다.<br><br>

## 1. Entity<br>
@ManyToOne<br>
다대일(N:1)관계를 나타낼 때 사용되는 어노테이션입니다.<br>
Like Entity에서 Member와 Board Entity가 사용되기 때문에 단방향 관계입니다.<br><br>

다른 부분(repository, dto 등)에서는 이전에 사용되었던 어노테이션만으로 처리가 가능했습니다.

## 2. 이후 구현 방안<br>
1. 정렬 기능 추가<br>
현제 게시글과 댓글은 제작 날짜로 정렬되어있습니다.<br>
   이를 현재 추가된 추천, 좋아요를 기준으로 정렬하도록 하겠습니다.<br><br>
   
2. 검색 기능 추가<br>
현재 검색은 제목 + 내용을 검색합니다.<br>
   이를 제목, 내용, 제목 + 내용 으로 만들 수 있게 만들겠습니다.<br><br>
   

# 2021/06/10<br>
추천기능을 추가하긴 했지만 여러가지로 문제를 많이 격었으며 아직까지 개선해야할 점이 많습니다.<br><br>

## 1. @Query 어노테이션 사용<br>
@Query 어노테이션을 사용할 경우 sql 문법의 join과 count, order by 등을 사용하여 정렬된 데이터(entity)를 가져오는 것이 가능합니다. <br>
하지만 별도의 class나 interface를 별도로 제작하고 이에 맞춰 기존의 service 내부의 정렬 알고리즘 등을 크게 변경해야 할 수도 있습니다.<br>
예를들어 추천 갯수를 가져와 이를 기준으로 변경하기 위해서는 sql 문법의 count를 사용해야하며 이는 결과를 저장할 별도의 객체를 준비해야 합니다.<br>
형태가 가장 많이 변화할 것 같았기에 다른 방안을 찾아보았습니다.<br><br>

## 2. @Formula 어노테이션 사용<br>
Entity내부에 DB table column과 매칭되는 변수 이외에 다른 값에 적용시켜 이를 정렬 기준으로 사용하였습니다.<br>
@Formula(query)에서 query에 매칭되는 하나의 column을 가져와 매핑할 수 있습니다.<br>
단, 단 하나의 칼럼만을 매핑할 수 있기 때문에 주로 합계(sum), 갯수(count) 등을 사용할 경우에 주로 사용합니다.<br>
또한 기존의 정렬 방법 역시 Entity의 변수를 기준으로 이루어 졌기 때문에 정렬역시 손쉽게 진행될 수 있었습니다.<br><br>

## 3. 이후 구현 방안<br>
like, recommend 등 만들어진 클레스 네임이 형편없습니다. 이를 최우선적으로 수정합니다.<br>
또한 현제 추천을 기준으로 정렬을 할 수 있게 되었으나 선택적이지 않습니다.<br>
정렬과 검색을 선택적으로 할 수 있도록 수정합니다.<br><br>
1. 클레스 네임 수정<br>
2. 정렬 기능과 검샥 기능의 선택 추가