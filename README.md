### 프로젝트 설명

#### 구조

    ```aidl
    Root project 'kpay-point'
    +--- Project ':api'
    \--- Project ':core'
    ```

* 2개의 sub-moduble 로 구성되어 있습니다.
* core - JPA 기반의 CRUD 프로젝트
* api - Spring Web 기반의 프로젝트

#### 실행

```aidl
    ./gradlew api:bootRun
```

* 결과는 [다음](./docs/RESULT.md) 를 참고하세요

#### 테스트 실행

```aidl
    ./gradlew api:test
    
    결과는 아래의 HTML 파일로 확인
    - .//core/build/reports/tests/test/index.html
    - .//api/build/reports/tests/test/index.html
```

* 유닛테스트와, DataJpaTest, MockMvc 테스트를 준비하였다.
* JpaTest 로 끝나는 테스트 파일들은 Tag("SpringBoot") 를 붙여서 빠른 유닛테스트의 라이프 사이클에 방해가 되지 않도록 함
* `MemberShipRepositoryJpaTest` 에서는 기본으로 제공하는 data.sql 을 검증함, `혹시나 테스트 데이터가 원치 않게 변경되어서`, 그 테스트 데이터의 의존 하는 다른 테스트들을 신뢰할
  수 있도록 함

