# TravelLog API 명세서

## 목차

1. [개요](#1-개요)
2. [공통 규격](#2-공통-규격)
3. [Auth](#3-auth)
4. [Users](#4-users)
5. [Trips](#5-trips)
6. [Photos](#6-photos)
7. [Places](#7-places)
8. [Memos](#8-memos)
9. [Map](#9-map)
10. [Badges](#10-badges)
11. [Schedules](#11-schedules)
12. [Friends](#12-friends)

---

## 1. 개요

| 항목 | 값 |
|---|---|
| Base URL | `https://api.travellog.app/v1` |
| 프로토콜 | HTTPS |
| 인증 방식 | Bearer Token (JWT) |
| 날짜 형식 | ISO 8601 (`2024-08-01T09:00:00Z`) |
| 좌표 형식 | WGS84 (lat/lng, double) |

---

## 2. 공통 규격

### 요청 헤더

```
Authorization: Bearer {accessToken}
Content-Type: application/json
Accept-Language: ko   # 오류 메시지 언어
```

### 응답 형식

**성공**
```json
{
  "data": { ... }
}
```

**목록 (페이지네이션)**
```json
{
  "data": {
    "items": [ ... ],
    "nextCursor": "cursor_string",   // null이면 마지막 페이지
    "hasMore": true
  }
}
```

**오류**
```json
{
  "error": {
    "code": "TRIP_NOT_FOUND",
    "message": "여행을 찾을 수 없습니다."
  }
}
```

### HTTP 상태 코드

| 코드 | 의미 |
|---|---|
| 200 | 성공 |
| 201 | 생성 성공 |
| 204 | 성공 (응답 바디 없음) |
| 400 | 잘못된 요청 |
| 401 | 인증 필요 |
| 403 | 권한 없음 |
| 404 | 리소스 없음 |
| 409 | 충돌 (중복 등) |
| 500 | 서버 오류 |

### 페이지네이션 쿼리 파라미터

| 파라미터 | 타입 | 설명 |
|---|---|---|
| `cursor` | string | 이전 응답의 nextCursor 값 |
| `limit` | int | 한 번에 가져올 수 (기본값: 20, 최대: 100) |

---

## 3. Auth

### 3-1. 회원가입

`POST /auth/sign-up`

**Request**
```json
{
  "provider": "APPLE",          // APPLE | GOOGLE
  "idToken": "oauth_id_token",
  "name": "정소윤",
  "handle": "soyoon"            // 앱 내 고유 핸들, @handle
}
```

**Response** `201`
```json
{
  "data": {
    "accessToken": "jwt_access_token",
    "refreshToken": "jwt_refresh_token",
    "user": { /* User 객체 */ }
  }
}
```

---

### 3-2. 로그인

`POST /auth/sign-in`

**Request**
```json
{
  "provider": "APPLE",
  "idToken": "oauth_id_token"
}
```

**Response** `200`
```json
{
  "data": {
    "accessToken": "jwt_access_token",
    "refreshToken": "jwt_refresh_token",
    "user": { /* User 객체 */ }
  }
}
```

---

### 3-3. 토큰 갱신

`POST /auth/token/refresh`

**Request**
```json
{
  "refreshToken": "jwt_refresh_token"
}
```

**Response** `200`
```json
{
  "data": {
    "accessToken": "jwt_access_token",
    "refreshToken": "jwt_refresh_token"
  }
}
```

---

### 3-4. 로그아웃

`POST /auth/sign-out`

**Request** _(없음)_

**Response** `204`

---

## 4. Users

### User 객체

```json
{
  "id": "user_01J...",
  "name": "정소윤",
  "handle": "soyoon",
  "avatarEmoji": "🌸",
  "title": "여행자",
  "visibility": "PRIVATE",       // PRIVATE | FRIENDS
  "storageUsedBytes": 1073741824,
  "storageTotalBytes": 5368709120,
  "isPro": false
}
```

---

### 4-1. 내 프로필 조회

`GET /users/me`

**Response** `200`
```json
{
  "data": { /* User 객체 */ }
}
```

---

### 4-2. 내 프로필 수정

`PATCH /users/me`

**Request** _(변경할 필드만 포함)_
```json
{
  "name": "소윤",
  "avatarEmoji": "🌿",
  "title": "모험가",
  "visibility": "FRIENDS"
}
```

**Response** `200`
```json
{
  "data": { /* 수정된 User 객체 */ }
}
```

---

### 4-3. 내 통계 조회

`GET /users/me/stats`

**Response** `200`
```json
{
  "data": {
    "countryCnt": 5,
    "stateCnt": 12,
    "photoCnt": 348,
    "placeCnt": 87,
    "tripCnt": 10,
    "totalSteps": 1540200
  }
}
```

---

### 4-4. 친구 프로필 조회

`GET /users/{userId}`

**Response** `200`
```json
{
  "data": {
    "id": "user_01J...",
    "name": "김여행",
    "handle": "travel_kim",
    "avatarEmoji": "✈️",
    "title": "탐험가",
    "tripCnt": 7,
    "countryCnt": 4
  }
}
```

---

## 5. Trips

### Trip 객체

```json
{
  "id": "trip_01J...",
  "title": "2024 오사카",
  "flag": "🇯🇵",
  "country": "일본",
  "cities": ["오사카", "교토"],
  "startDate": "2024-03-10",
  "endDate": "2024-03-15",
  "photoCnt": 42,
  "placeCnt": 10,
  "badgeCnt": 3,
  "accentColor": "#FF6B6B",
  "coverTiles": [
    { "uri": "https://cdn.travellog.app/photos/..." },
    { "uri": "https://cdn.travellog.app/photos/..." }
  ],
  "createdAt": "2024-03-20T10:00:00Z",
  "updatedAt": "2024-03-20T10:00:00Z"
}
```

---

### 5-1. 여행 목록 조회

`GET /trips`

**Query Parameters**

| 파라미터 | 타입 | 설명 |
|---|---|---|
| `q` | string | 여행 이름 또는 지역명 검색 |
| `cursor` | string | 페이지네이션 커서 |
| `limit` | int | 기본값 20 |

**Response** `200`
```json
{
  "data": {
    "items": [ /* Trip 객체 배열 */ ],
    "nextCursor": "cursor_string",
    "hasMore": false
  }
}
```

---

### 5-2. 여행 생성

`POST /trips`

**Request**
```json
{
  "title": "2024 오사카",
  "startDate": "2024-03-10",
  "endDate": "2024-03-15"
}
```

**Response** `201`
```json
{
  "data": { /* Trip 객체 */ }
}
```

---

### 5-3. 여행 상세 조회

`GET /trips/{tripId}`

**Response** `200`
```json
{
  "data": { /* Trip 객체 */ }
}
```

---

### 5-4. 여행 수정

`PATCH /trips/{tripId}`

**Request** _(변경할 필드만 포함)_
```json
{
  "title": "2024 오사카·교토",
  "endDate": "2024-03-16"
}
```

**Response** `200`
```json
{
  "data": { /* 수정된 Trip 객체 */ }
}
```

---

### 5-5. 여행 삭제

`DELETE /trips/{tripId}`

**Response** `204`

---

### 5-6. 여행 타임라인 조회

`GET /trips/{tripId}/timeline`

사진·장소·메모를 시간 순으로 합쳐서 반환합니다.

**Query Parameters**

| 파라미터 | 타입 | 설명 |
|---|---|---|
| `cursor` | string | 페이지네이션 커서 |
| `limit` | int | 기본값 30 |

**Response** `200`
```json
{
  "data": {
    "items": [
      {
        "type": "PHOTO",
        "timestamp": "2024-03-10T09:00:00Z",
        "photo": { /* Photo 객체 */ }
      },
      {
        "type": "PLACE",
        "timestamp": "2024-03-10T11:30:00Z",
        "place": { /* Place 객체 */ }
      },
      {
        "type": "MEMO",
        "timestamp": "2024-03-10T14:00:00Z",
        "memo": { /* Memo 객체 */ }
      }
    ],
    "nextCursor": "cursor_string",
    "hasMore": true
  }
}
```

---

## 6. Photos

### Photo 객체

```json
{
  "id": "photo_01J...",
  "tripId": "trip_01J...",
  "mediaType": "PHOTO",           // PHOTO | VIDEO
  "url": "https://cdn.travellog.app/photos/...",
  "thumbnailUrl": "https://cdn.travellog.app/thumbnails/...",
  "lat": 34.6937,
  "lng": 135.5023,
  "city": "오사카",
  "country": "일본",
  "placeName": "도톤보리",
  "memo": "진짜 맛있었다",           // 사진 부연 설명
  "emoji": "🍜",
  "accentColor": "#F4A261",
  "takenAt": "2024-03-10T09:00:00Z",
  "createdAt": "2024-03-20T10:00:00Z"
}
```

---

### 6-1. Presigned URL 발급

`POST /photos/presigned-url`

**Request**
```json
{
  "fileName": "IMG_0042.jpg",
  "contentType": "image/jpeg",
  "count": 3                      // 한 번에 여러 장 업로드 시
}
```

**Response** `200`
```json
{
  "data": {
    "uploads": [
      {
        "photoKey": "uploads/user_01J.../uuid.jpg",
        "presignedUrl": "https://s3.amazonaws.com/...",
        "expiresAt": "2024-03-20T10:15:00Z"
      }
    ]
  }
}
```

> 클라이언트는 `presignedUrl`로 직접 PUT 요청하여 파일을 업로드한 뒤, `photoKey`를 포함해 **6-2**를 호출합니다.

---

### 6-2. 사진 등록

`POST /photos`

**Request**
```json
{
  "tripId": "trip_01J...",
  "photoKey": "uploads/user_01J.../uuid.jpg",
  "mediaType": "PHOTO",
  "takenAt": "2024-03-10T09:00:00Z",
  "lat": 34.6937,
  "lng": 135.5023,
  "placeName": "도톤보리",
  "city": "오사카",
  "country": "일본",
  "memo": "진짜 맛있었다",
  "emoji": "🍜"
}
```

> `lat`/`lng`이 없으면 `placeName`/`city`를 직접 입력한 것으로 처리합니다.

**Response** `201`
```json
{
  "data": {
    "photo": { /* Photo 객체 */ },
    "awardedBadgeIds": ["badge_01J...", "badge_01K..."]  // 새로 획득한 뱃지 ID 목록 (없으면 빈 배열)
  }
}
```

---

### 6-3. 사진 수정

`PATCH /photos/{photoId}`

**Request** _(변경할 필드만 포함)_
```json
{
  "tripId": "trip_01J...",
  "placeName": "도톤보리 골목",
  "memo": "다음에 또 오고 싶다",
  "emoji": "🎉"
}
```

**Response** `200`
```json
{
  "data": { /* 수정된 Photo 객체 */ }
}
```

---

### 6-4. 사진 삭제

`DELETE /photos/{photoId}`

**Response** `204`

---

## 7. Places

### Place 객체

```json
{
  "id": "place_01J...",
  "tripId": "trip_01J...",
  "name": "도톤보리",
  "category": "관광지",
  "address": "일본 오사카부 오사카시 주오구 도톤보리",
  "lat": 34.6687,
  "lng": 135.5015,
  "city": "오사카",
  "country": "일본",
  "rating": 4.5,
  "reviewCount": 1200,
  "emoji": "🏮",
  "accentColor": "#E63946",
  "visitedAt": "2024-03-10T11:30:00Z",
  "createdAt": "2024-03-20T10:00:00Z"
}
```

---

### 7-1. 장소 검색

`GET /places/search`

외부 지도 API 결과를 서버가 중계합니다.

**Query Parameters**

| 파라미터 | 타입 | 설명 |
|---|---|---|
| `q` | string | 검색어 (필수) |
| `lat` | double | 기준 위도 (옵션, 근처 우선 정렬) |
| `lng` | double | 기준 경도 (옵션) |

**Response** `200`
```json
{
  "data": {
    "items": [
      {
        "name": "도톤보리",
        "category": "관광지",
        "address": "일본 오사카부 오사카시 주오구",
        "lat": 34.6687,
        "lng": 135.5015
      }
    ]
  }
}
```

---

### 7-2. 장소 등록

`POST /places`

**Request**
```json
{
  "tripId": "trip_01J...",
  "name": "도톤보리",
  "category": "관광지",
  "address": "일본 오사카부 오사카시 주오구",
  "lat": 34.6687,
  "lng": 135.5015,
  "city": "오사카",
  "country": "일본",
  "visitedAt": "2024-03-10T11:30:00Z",
  "emoji": "🏮"
}
```

**Response** `201`
```json
{
  "data": {
    "place": { /* Place 객체 */ },
    "awardedBadgeIds": ["badge_01J..."]
  }
}
```

---

### 7-3. 장소 수정

`PATCH /places/{placeId}`

**Request** _(변경할 필드만 포함)_
```json
{
  "emoji": "🌟",
  "visitedAt": "2024-03-10T12:00:00Z"
}
```

**Response** `200`
```json
{
  "data": { /* 수정된 Place 객체 */ }
}
```

---

### 7-4. 장소 삭제

`DELETE /places/{placeId}`

**Response** `204`

---

## 8. Memos

여행 컨텐츠로 남기는 메모입니다. 사진 설명(caption)은 Photo의 `memo` 필드로 처리합니다.

### Memo 객체

```json
{
  "id": "memo_01J...",
  "tripId": "trip_01J...",
  "text": "오늘 하루 정말 알차게 보냈다. 내일은 교토로 이동!",
  "city": "오사카",
  "accentColor": "#2A9D8F",
  "createdAt": "2024-03-10T22:00:00Z"
}
```

---

### 8-1. 메모 등록

`POST /memos`

**Request**
```json
{
  "tripId": "trip_01J...",
  "text": "오늘 하루 정말 알차게 보냈다.",
  "city": "오사카",
  "accentColor": "#2A9D8F"
}
```

**Response** `201`
```json
{
  "data": { /* Memo 객체 */ }
}
```

---

### 8-2. 메모 수정

`PATCH /memos/{memoId}`

**Request**
```json
{
  "text": "오늘 하루 정말 알차게 보냈다. 내일은 교토로 이동!",
  "accentColor": "#264653"
}
```

**Response** `200`
```json
{
  "data": { /* 수정된 Memo 객체 */ }
}
```

---

### 8-3. 메모 삭제

`DELETE /memos/{memoId}`

**Response** `204`

---

## 9. Map

### 9-1. 내 핀 목록 조회

`GET /map/pins`

지도에 표시할 핀의 최소 정보만 반환합니다. 클러스터링은 클라이언트에서 처리합니다.

**Response** `200`
```json
{
  "data": {
    "items": [
      {
        "photoId": "photo_01J...",
        "thumbnailUrl": "https://cdn.travellog.app/thumbnails/...",
        "lat": 34.6937,
        "lng": 135.5023,
        "takenAt": "2024-03-10T09:00:00Z"
      }
    ]
  }
}
```

---

### 9-2. 친구 핀 목록 조회

`GET /map/friends/pins`

**Response** `200`
```json
{
  "data": {
    "items": [
      {
        "userId": "user_01J...",
        "name": "김여행",
        "avatarEmoji": "✈️",
        "photoId": "photo_01K...",
        "thumbnailUrl": "https://cdn.travellog.app/thumbnails/...",
        "lat": 35.6762,
        "lng": 139.6503,
        "takenAt": "2024-03-08T14:00:00Z"
      }
    ]
  }
}
```

---

## 10. Badges

### Badge 객체

```json
{
  "id": "badge_01J...",
  "type": "STATE",               // COUNTRY | STATE
  "countryId": "JP",
  "name": "오사카",
  "emoji": "🏮",
  "label": "오사카 방문",
  "isRare": false,
  "designId": "design_default",
  "earnedAt": "2024-03-10T09:00:00Z"
}
```

---

### 10-1. 내 뱃지 목록 조회

`GET /users/me/badges`

**Query Parameters**

| 파라미터 | 타입 | 설명 |
|---|---|---|
| `type` | string | `COUNTRY` 또는 `STATE` 필터 (옵션) |

**Response** `200`
```json
{
  "data": {
    "items": [ /* Badge 객체 배열 */ ]
  }
}
```

---

### 10-2. 뱃지 단건 조회

`GET /badges/{badgeId}`

사진·장소 등록 후 `awardedBadgeIds`로 받은 ID를 조회합니다.

**Response** `200`
```json
{
  "data": { /* Badge 객체 */ }
}
```

---

### 10-3. 뱃지 디자인 목록 조회

`GET /badges/designs`

구매 가능한 디자인 목록을 반환합니다.

**Response** `200`
```json
{
  "data": {
    "items": [
      {
        "id": "design_gold",
        "name": "골드 에디션",
        "previewEmoji": "🥇",
        "priceKrw": 1900,
        "isPurchased": false
      }
    ]
  }
}
```

---

### 10-4. 뱃지 디자인 변경

`PATCH /badges/{badgeId}/design`

**Request**
```json
{
  "designId": "design_gold"
}
```

**Response** `200`
```json
{
  "data": { /* 수정된 Badge 객체 */ }
}
```

---

## 11. Schedules

### Schedule 객체

```json
{
  "id": "schedule_01J...",
  "title": "여름 유럽 여행",
  "destinations": [
    { "country": "프랑스", "city": "파리", "flagEmoji": "🇫🇷" },
    { "country": "이탈리아", "city": "로마", "flagEmoji": "🇮🇹" }
  ],
  "startDate": "2024-07-10",
  "endDate": "2024-07-20",
  "color": "#A8DADC",
  "createdAt": "2024-06-01T10:00:00Z"
}
```

---

### 11-1. 일정 목록 조회

`GET /schedules`

**Response** `200`
```json
{
  "data": {
    "items": [ /* Schedule 객체 배열 */ ]
  }
}
```

---

### 11-2. 일정 생성

`POST /schedules`

**Request**
```json
{
  "title": "여름 유럽 여행",
  "destinations": [
    { "country": "프랑스", "city": "파리", "flagEmoji": "🇫🇷" }
  ],
  "startDate": "2024-07-10",
  "endDate": "2024-07-20",
  "color": "#A8DADC"
}
```

**Response** `201`
```json
{
  "data": { /* Schedule 객체 */ }
}
```

---

### 11-3. 일정 상세 조회

`GET /schedules/{scheduleId}`

**Response** `200`
```json
{
  "data": { /* Schedule 객체 */ }
}
```

---

### 11-4. 일정 수정

`PATCH /schedules/{scheduleId}`

**Request** _(변경할 필드만 포함)_
```json
{
  "title": "2024 파리·로마",
  "endDate": "2024-07-22"
}
```

**Response** `200`
```json
{
  "data": { /* 수정된 Schedule 객체 */ }
}
```

---

### 11-5. 일정 삭제

`DELETE /schedules/{scheduleId}`

**Response** `204`

---

### 11-6. 목적지 인기 장소 추천

`GET /schedules/destinations/suggestions`

**Query Parameters**

| 파라미터 | 타입 | 설명 |
|---|---|---|
| `city` | string | 도시명 (예: `도쿄`) (필수) |

**Response** `200`
```json
{
  "data": {
    "city": "도쿄",
    "places": [
      {
        "name": "신주쿠",
        "category": "번화가",
        "description": "도쿄 최대 번화가",
        "emoji": "🏙️"
      },
      {
        "name": "아사쿠사",
        "category": "관광지",
        "description": "센소지 절이 있는 전통 거리",
        "emoji": "⛩️"
      }
    ],
    "tips": [
      "교통: 스이카 카드를 미리 구매하면 편리합니다.",
      "운영시간: 대부분 관광지는 오전 9시~오후 5시 운영"
    ]
  }
}
```

---

## 12. Friends

### 12-1. 친구 목록 조회

`GET /friends`

**Response** `200`
```json
{
  "data": {
    "items": [
      {
        "userId": "user_01J...",
        "name": "김여행",
        "handle": "travel_kim",
        "avatarEmoji": "✈️"
      }
    ]
  }
}
```

---

### 12-2. 친구 요청 전송

`POST /friends/requests`

**Request**
```json
{
  "targetUserId": "user_01J..."
}
```

**Response** `201`
```json
{
  "data": {
    "requestId": "req_01J...",
    "status": "PENDING"
  }
}
```

---

### 12-3. 받은 친구 요청 목록

`GET /friends/requests`

**Response** `200`
```json
{
  "data": {
    "items": [
      {
        "requestId": "req_01J...",
        "fromUser": {
          "userId": "user_01K...",
          "name": "이모험",
          "handle": "adventure_lee",
          "avatarEmoji": "🧗"
        },
        "requestedAt": "2024-03-20T09:00:00Z"
      }
    ]
  }
}
```

---

### 12-4. 친구 요청 수락/거절

`PATCH /friends/requests/{requestId}`

**Request**
```json
{
  "action": "ACCEPT"   // ACCEPT | REJECT
}
```

**Response** `200`
```json
{
  "data": {
    "requestId": "req_01J...",
    "status": "ACCEPTED"   // ACCEPTED | REJECTED
  }
}
```

---

### 12-5. 친구 삭제

`DELETE /friends/{userId}`

**Response** `204`