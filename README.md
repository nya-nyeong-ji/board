# spring jpa를 이용하여 간단한 게시판 구현<br>
그렇기 때문에 css등의 디자인적 요소는 신경쓰지 않았습니다.

# 게시물을 이용한 등록, 수정, 삭제, 조회 기능<br>
SPRING JPA를 이용한 데이터 등록, 수정, 삭제, 조회를 구현

# 2021/05/27<br>
1. Controller<br><br>
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
   

2. Service<br><br>
@Service<br>
   서비스 계층인 것을 명시하는 어노테이션입니다.<br><br>
   @AllArgsConstructor<br>
   위 Controller에서 사용했듯이 여기선 Repository 객체를 주입합니다.<br><br>
   @Transactional<br>
   선언적 트랜잭션이라고 합니다.<br>
   트랜잭션을 적용하는 어노테이션입니다.<br>
   선언된 메서드를 진행하는 동안 원자성, 일관성, 격리성, 지속성을 유지할 수록 합니다.
  
 
3. Repository<br><br>
인터페이스를 정의하고 JpaRepository 인터페이스를 상속받게 됩니다.<br>
   이 과정에서 Entity와 PK의 타입을 명시하게 됩니다.<br><br>
   위 Service에서 findById(), deleteById()등이 사용되었으나 repository에서 이를 확인할 수 없습니다<br>
   위 메서드들은 JpaRepository에 정의되어 있습니다.
   

4. Entity<br><br>
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
   

5. TimeEntity<br><br>
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
   

6. @EnableJpaAuditing<br>
JPA Auditing을 활성화 시키기 위해서는 Application에서 @EnableJpaAuditing 어노테이션을 추가해야 합니다.
   

7. 이후 구현 방향성<br>
게시판에 필요한 다른 entity(ex. 회원, 댓글 등) 구현
추가될 entity에 맞춰 필요한 기능들 추가
   

# 2021/05/28<br>
1. 댓글 entity 추가<br>
댓글 entity가 추가됨에 따라 필요한 dto, repository, service 등이 추가되었습니다.
   
2. 기존 오류 수정<br>
게시글 수정의 오류 수정 (/board/update.html에 있던 문제점 수정)
   
3. 이후 구현 방향성<br>
페이징 추가<br>
   유저 혹은 멤버를 이용한 로그인 기능 추가<br>
   controller 완전 분리<br>
   (현재 게시물의 세부사항 조회와 댓글 리스트 조회를 한번에 호출해야 합니다.)<br>
   (또한 게시물 삭제 시에 해당하는 댓글을 한번에 삭제해야 합니다.)<br>
   (위 두가지 상황때문에 controller를 완벽하게 분리하지 못했습니다.)