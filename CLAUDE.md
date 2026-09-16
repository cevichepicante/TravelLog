# TravelLog — 프로젝트 가이드

## 서비스 개요
사진의 GPS 정보를 읽어 장소·지역을 자동 태깅하고, 지도 위에서 여행 기록을 되돌아보는 Android 앱.
지도(지구본/핀), 기록(여행 앨범), 일정, 프로필(뱃지·마그넷) 4개 탭 + 기록 추가 플로우로 구성된다.

## 기술 스택
- 언어: Kotlin / UI: Jetpack Compose + Material3
- DI: Hilt / 네비게이션: Navigation Compose
- 네트워크: Retrofit + OkHttp + Kotlin Serialization
- 로컬 저장: DataStore (Preferences) / 이미지: Coil
- 지도: Mapbox Maps SDK v11(지구본), 네이버맵(국내)·구글맵(해외) 핀 지도
- 백엔드: Firebase (Auth/Firestore/Storage) 또는 Supabase (미정)
- 최소 SDK 26 / 타겟 SDK 35

## 아키텍처
Multi-module Clean Architecture (Presentation–Domain–Data 레이어 분리).
Presentation 레이어는 MVVM(View + ViewModel/StateFlow) 패턴을 따른다.
의존성 방향: `feature(Presentation) → core:model(Domain) ← core:data(Data)`.
Presentation은 Domain(`core:model`)에만 의존하며 Data(`core:data`)를 직접 의존하지 않는다 (Dependency Rule).
Repository 인터페이스는 `core:model`(Domain)에 정의하고, 구현체는 `core:data`(Data)에 둔다. 역방향 의존 금지.
`app` 모듈은 DI 조립을 담당하는 composition root로서 예외적으로 `core:data`를 직접 의존한다.

## 모듈 구조
| 모듈 | 역할 |
|---|---|
| `:app` | Application, MainActivity, NavHost |
| `:feature:map` `:records` `:schedule` `:profile` `:add-record` | 화면별 Presentation |
| `:core:model` | Domain — 공유 도메인 모델 + Repository 인터페이스 |
| `:core:data` | Data — Repository 구현체, Remote/Local Data Source |
| `:core:network` | API 클라이언트, 인증 인터셉터 |
| `:core:ui` | 공통 Compose 컴포넌트/테마 |
| `:core:platform` | EXIF 파싱, Health Connect, 권한 등 플랫폼 기능 |

## 컨벤션
- Gradle: Kotlin DSL, 의존성은 Version Catalog(`gradle/libs.versions.toml`) 필수 사용
- 모듈 간 의존성은 `api`/`implementation` 구분, 순환 의존 금지
- 새 기능 추가 시 Data → Domain → Presentation 순서로 구현
- 도메인 레이어(`core:model`)에는 `android.*` import 금지 (순수 Kotlin)

## 현재 상태 참고
로컬 Room DB와 기존 Repository 계층이 제거 진행 중이며, 위 Domain/Data 분리 규칙에 맞춰 재구축이 필요한 과도기 상태다.
