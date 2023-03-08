# board-jpa
jpa로 구현한 board 미션 레포입니다

**간단 게시판 구현**

- 프론트는 구현하지 않습니다 api만 구현합니다
- **jdk14 버전 이상 사용**
- **lombok은 사용하지 말아주세요**
- JPA사용
- DB는 h2 또는 mysql을 사용합니다
- api 구현(최대한 restful하게 구현!) 필요한 api는 추가로 구현하셔도 됩니다
    - 게시글 조회
        - 페이징 조회 → pageable 사용
        - 단건 조회 → 게시판 id로 조회
    - 게시글 작성
    - 게시글 수정
- rest-docs를 사용해서 문서화 합니다 (선택사항)
    - rest-docs를 사용한 문서화 + controller단 테스트 코드를 작성해봐요~!
- ExceptionHandler와 validation을 추가해주세요 (선택사항)

### [RESTDocs API 명세](src/main/resources/static/docs/index.html)
