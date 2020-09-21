장소 검색 서비스 API
======================



### 검색 API를 이용하여 키워드로 장소를 검색할 수 있는 서비스 API 입니다.
##### 현재는 카카오에서 제공하는 API만 적용되어 있으며 인터페이스 구현을 통해 확장이 가능합니다.
> https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword
> https://apis.map.kakao.com/web/guide/#bigmapurl



### 시스템 확장이 용이하도록 캐시(Redis)를 적용하였으며, 내부 스케줄러의 실행 제한을 옵션으로 제어 가능합니다.
##### 내부 스케쥴러:
> 사용자의 검색어 호출 집계를 위한 스케쥴러로 시스템 성능을 위해 캐시를 활용합니다.
> Process: 검색 -> 캐시에 저장 -> 스케쥴러에서 지정된 주기에 동작하여 DB로 저장 



### 서비스 API 이용을 위해서는 아래의 순서를 준수하여야 합니다.
> 1. 사용자 로그인 후 JWT 획득
> 2. 발급받은 JWT 사용하여 서비스 API 이용
> JWT 유효시간은 1시간 입니다.



### 현재 버전에서 접속정보는 기동시 자동 생성됩니다.

##### 자동 생성 정보:
>    - tester1 / 123451
>    - tester2 / 123452
>    - tester3 / 123453
>    - tester4 / 123454
>    - tester5 / 123455



### 제공되는 API :

##### 1. 로그인 API: 
* 로그인   POST   /v1/login

##### 2. 사용자 API: 
* 사용자 조회  GET       /v1/user
* 사용자 생성  POST    /v1/user
* 사용자 삭제  DELETE /v1/user/{id}
* 사용자 조회  GET      /v1/user/{id}
* 사용자 수정  PATCH /v1/user/{id}
* 사용자 변경  PUT     /v1/user/{id}

##### 3. 검색 API: 
* 장소 검색  GET   /v1/search/place/{vendor}
* 인기 검색어 목록(TOP10)    GET   /v1/search/vest-keyword/{vendor}


### 기동 후 swagger 를 통해 명세서 확인, 기능 테스트를 할 수 있습니다.
> http://localhost:8080/swagger-ui.html



* * *

Features
--------

#### * Spring Boot 2.3.3.RELEASE
#### * Spring JPA
#### * Spring Redis
#### * Spring Security
#### * JWT - io.jsonwebtoken.jjwt:0.9.1
#### * Swagger - io.springfox.springfox-swagger2
#### * JSON - com.google.code.gson
#### * Use API client - org.apache.httpcomponents:httpclient
#### * Lombok - org.projectlombok
#### * embeded H2 Database
>   기동 후 웹 콘솔 접속: http://localhost:8080/h2-console
#### * Redis
##### Redis 설치 정보를 application.yml에 작성합니다.
>   redis:  
>     host: localhost  
>     password: ****  
>     port: 6379  
