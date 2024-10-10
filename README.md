# ReadMeExample

## 💻 `프로젝트 소개`
비자프로젝트 어플리케이션

비자 신청을 간편하게 함으로써 국내 방문 관광객 유치를 늘리고, 
기존에 국내에서 장기 거주하던 외국인들의 비자 접근성을 확대하여 
보다 편리하게 비자 관련 절차를 처리할 수 있도록 지원하고자 합니다. 

따라서, 이 프로젝트는 국내 관광 산업의 활성화와 외국인의 장기 체류 환경 개선에 기여하는 중요한 의미를 갖습니다
<br>

##  ⌨️ `개발 기간`
- 기획단계 08/27~09/11
- 개발단계 09/12 ~ 10/11

### 🧑‍🤝‍🧑 `멤버구성`
 - 팀장: 강연진 
 - 팀원: 권보현
   
### ⚙️ `개발 환경`

- CSS . Html . java . JavaScript . Spring Framework . spring security. MySql . jpa. jta. jwt. webpack. node.js

### 📂 `패키지구조`


```
─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─ohgiraffers
│  │  │          └─visaproject
│  │  │              │  VisaProjectApplication.java
│  │  │              │
│  │  │              ├─common
│  │  │              │      AuthConstants.java
│  │  │              │      ResponseDTO.java
│  │  │              │      ResponseMessage.java
│  │  │              │
│  │  │              ├─config
│  │  │              │      ApplyConfig.java
│  │  │              │      AuthConstants.java
│  │  │              │      BeanConfiguration.java
│  │  │              │      CorsConfig.java
│  │  │              │      SwaggerConfig.java
│  │  │              │      WebConfig.java
│  │  │              │      WebSecurityConfig.java
│  │  │              │
│  │  │              ├─controller
│  │  │              │      ApplyController.java
│  │  │              │      AuthController.java
│  │  │              │      EmailController.java
│  │  │              │      MemberManagerController.java
│  │  │              │      ReservationController.java
│  │  │              │      ResourceNotFoundException.java
│  │  │              │      StatisticsController.java
│  │  │              │      TestController.java
│  │  │              │
│  │  │              ├─filter
│  │  │              │      CustomAuthenticationFilter.java
│  │  │              │      HeaderFilter.java
│  │  │              │      JwtAuthorizationFilter.java
│  │  │              │
│  │  │              ├─handler
│  │  │              │      CustomAuthenticationProvider.java
│  │  │              │      CustomAuthFailUserHandler.java
│  │  │              │      CustomAuthSuccessHandler.java
│  │  │              │
│  │  │              ├─model
│  │  │              │  ├─dto
│  │  │              │  │      AdminLoginDTO.java
│  │  │              │  │      ApplyDTO.java
│  │  │              │  │      CreateUserRequest.java
│  │  │              │  │      ErrorResponse.java
│  │  │              │  │      LoginRequest.java
│  │  │              │  │      LoginResponse.java
│  │  │              │  │      MemberManagerDTO.java
│  │  │              │  │      ReservationDTO.java
│  │  │              │  │      StatisticsDTO.java
│  │  │              │  │      TokenDTO.java
│  │  │              │  │
│  │  │              │  ├─entity
│  │  │              │  │      AdminEntity.java
│  │  │              │  │      ApplyEntity.java
│  │  │              │  │      MemberManagerEntity.java
│  │  │              │  │      ReservationEntity.java
│  │  │              │  │      Role.java
│  │  │              │  │      Statistics.java
│  │  │              │  │
│  │  │              │  ├─repository
│  │  │              │  │      ApplyRepository.java
│  │  │              │  │      MemberManagerRepository.java
│  │  │              │  │      ReservationRepository.java
│  │  │              │  │      StatisticsRepository.java
│  │  │              │  │
│  │  │              │  └─service
│  │  │              │          ApplyService.java
│  │  │              │          CustomUserDetails.java
│  │  │              │          CustomUserDetailService.java
│  │  │              │          MailService.java
│  │  │              │          MemberManagerService.java
│  │  │              │          ReservationService.java
│  │  │              │          StatisticsService.java
│  │  │              │
│  │  │              └─util
│  │  │                      ConvertUtil.java
│  │  │                      TokenUtils.java
│  │  │
│  │  └─resources
│  │      │  application.yml
│  │      │
│  │      ├─static
│  │      │  │  index.html
│  │      │  │
│  │      │  └─build
│  │      └─templates
│  │          └─main
│  └─test
│      └─java
│          └─com
│              └─ohgiraffers
│                  └─visaproject
│                          VisaProjectApplicationTests.java
│
└─uploads
        1b4239e1-0ce9-44cb-9c93-870f023e62ac_확인용.txt
        20240926_175318_셈플첨부파일.txt
        20240926_180922_첨부확인.txt
        2b8363e2-4fb3-45e3-a8ff-910e9b5683fb_셈플첨부파일.txt
        39053047-f8cf-4d04-9798-5ceffd2da7a6_셈플첨부파일.txt
        3a6904c0-30fd-4028-af1d-75198f2ec409_첨부확인.txt
        4850a9cb-6880-4cb8-b0c4-544f672cc273_셈플첨부파일.txt
        5cefd8c8-07ee-4ca5-a28b-704ab9bd6cfc_첨부확인.txt
        78229ac1-4a06-4d53-a1d6-2f46e7b8e1d5_셈플첨부파일.txt
        84a7bdd7-2ee6-440e-98b5-aea3fc502777_첨부확인.txt
        8bea54f8-fb16-4bda-9539-3b15a8637e75_셈플첨부파일.txt
        926ae399-e212-49da-9213-a74985733024_첨부확인.txt
        c59d8113-96db-4b83-ae6b-6cfc0c7b9d78_셈플첨부파일.txt
        ca8c16fd-5a3c-4638-b6e1-6cb416fb647a_셈플첨부파일.txt
        d66c4a50-f64e-4b33-873b-bf5cd6cf0ece_첨부확인.txt
        셈플첨부파일.txt

  ```              
    
## 📌 주요 기능
###  🖱️ 비자신청기능
- 비회원이 비자신청을 하면 비자 종류가뜨고 특정 비자를 선택하면 신청에 필요한 서류가 안내가된다
- 비회원이 서류안내페이지에서 신청버튼을 누르면 비자신청 페이지로 이동하며,
- 이름, 신청사유, 첨부파일을 넣고 신청버튼을 누르면 비자신청에 관한 내용이 API를 거쳐 백엔드 데이터베이스에 저장된다
 
### 📋 예약기능
- 회원이아니면 예약을 할수없다, 로그인한 회원은 예약을 할수있다.
- 예약날짜, 예약시간, 이름을 입력하면 예약정보가 node서버로 전송이되고 5000번서버에서 8080서버로 데이터 전송을하여 저장을 할수있다.

###  🖱️ 회원관리
- 로그인 : 비회원이 로그인에서 아이디 와 비밀번호를 입력했을때 데이터베이스에 존재하는 아이디와 비밀번호이면 메인페이지로 이동
- 회원가입 : 비회원은 이름 닉네임 이메일 전화번호 이메일 을 입력해야하며 이메일인증을기능을 통해 인증번호를 입력하면 회원가입을 할수있다.
            프론트앤드에서 회원가입 버튼을 누르면 가입데이터가 백앤드로 저장이된다.
- 마이페이지 : 로그인한 회원의 닉네임 이메일 전화번호를 확인할 수 있다.

## 🗣️ 후기
- ## 강연진 :
- 느낀점:  RESTAPI나  리엑트등 수업시간에 한내용을 한번에 백프로 습득하지못했고  gpt에 많은 부분을 의존할수밖에 없었습니다.

- 기능적인 부분 : 페이지 양이 얼마나 될지, 프론트와 백앤드의 앤드포인트 페이지가 어떤게 될지 등의
                초반 레이아웃 계획을 잘 못짯기 때문에 페이지들의 정리가 잘 안되서 아쉬운부분이 있었기때문에 추후에 개선을 해보고자합니다.

- 코드개선 : 코드를 하나씩 늘려간 내용이라서 구조적으로  체계화 되거나 정리되지 않았습니다
            디자인적  요소가 들어가지 않았습니다.

-내용개선 : 충분히 내용이 있고  공부하고  분석해야  할 것들이  있었지만
          기능 구현 핑계로  미뤄두다가 결국 구현을 하지 못했습니다.
          프로젝트 진행 시 이점이나 수익성 이외에  진행 이후 리스크 등을 확인해서
          종합적인 분석을 해내지 못했습니다.

- ## 권보현 :
- 느낀점 : gpt에 많은 부분을 의존하였습니다. 추후에 공부를해서 보완을 해나가야겠다고 생각하였습니다.

- 기능적인 부분 :
- 로그인이후 로그인 버튼이 사라져야하는것, 전역상태 관리의 구조정리
- jwt토큰생성의 내용을 좀더 공부하도록 하겠습니다.

- 코드개선 : 구조적으로 내용파악이되어 누구나 알수있는 코드가 되지 못한면이 있어 개선을 해야합니다.
  

 
