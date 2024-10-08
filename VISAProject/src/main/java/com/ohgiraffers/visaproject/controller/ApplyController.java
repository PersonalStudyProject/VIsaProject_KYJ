package com.ohgiraffers.visaproject.controller;

import com.ohgiraffers.visaproject.common.ResponseMessage;
import com.ohgiraffers.visaproject.model.dto.ApplyDTO;
import com.ohgiraffers.visaproject.model.entity.ApplyEntity;
import com.ohgiraffers.visaproject.model.service.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000") //프론트앤드 도메인설정
@RequestMapping("/")
public class ApplyController {

    private final ApplyService applyService;

    @Autowired
    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @GetMapping("/upload")
    public ResponseEntity<String> handleGetUpload() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("GET 메소드는 지원되지 않습니다.");
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) {
//    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file,
//                                             @RequestPart("description")String description){

        // 파일 처리 로직 추가
//        try {
//            return ResponseEntity.ok().body("파일 업로드 성공");
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일업로드 실패");
//        }
//        String originalFilename = file.getOriginalFilename();
//        File destination = new File("upload/dir" + originalFilename);

        // 파일 저장 경로
        String uploadDirPath = System.getProperty("user.dir") + "/uploads"; //절대경로사용
//        String uploadDirPath = "uploads"; // 저장할 디렉터리 경로
        File uploadDir = new File(uploadDirPath);

        // 디렉터리가 존재하지 않으면 생성
        if (!uploadDir.exists()) {
            boolean dircreated = uploadDir.mkdirs(); //디렉토리 생성여부확인
//            uploadDir.mkdirs(); // 모든 상위 디렉터리도 생성
            if(!dircreated){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("message", "업로드 디렉터리 생성 실패"));
            }
        }

        try{
            //파일처리 로직 예:파일저장
//            String fileName = file.getOriginalFilename();
//            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            //파일의 원래 이름이 null또는 빈 문자열인지 확인
            String originalFileName = file.getOriginalFilename();
            if(originalFileName == null || originalFileName.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("message", "파일이름이 유효하지 않습니다"));
            }

            //특수문자제거(파일시스템에 문제를 일으킬수있는 문자들을 제거)
            originalFileName = originalFileName.replace("[^a-zA-Z0-9\\.\\-_]", "_");

            //사용자 지정패턴으로 파일이름지정(현재날짜+원래파일이름)
            String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
            String uniqueFileName = timeStamp+"_"+originalFileName; //날짜 시간포함

            String filePath = uploadDirPath + "/" + uniqueFileName;
//            String filePath = uploadDirPath +"/" + file.getOriginalFilename();
            System.out.println("파일 저장 경로: " + filePath);
            File dest = new File(filePath);

            //파일저장
            file.transferTo(dest);

            //파일을 로컬에 저장
//            Path path = Paths.get("uploads/" + fileName);
//            Files.write(path, file.getBytes());

            //응답반환
            Map<String, String> response = new HashMap<>();
            response.put("message", "파일 업로드 성공");
            response.put("fileName", uniqueFileName);
            return ResponseEntity.ok(response);
//            return ResponseEntity.ok().body("파일업로드 성공");

        }catch (IOException | IllegalStateException e) {
            e.printStackTrace(); // 예외 상세 내용을 서버 콘솔에 출력

            Map<String, String> response = new HashMap<>();
            response.put("message", "파일 저장 중 오류 발생: " + e.getMessage());

//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
    }

//    @RequestMapping("/")
//    public String build(){
//        return "build";
//    }

//    @RequestMapping(value = {"/", "/apply", "/another-path"}) // 모든 경로를 처리
//    public String index() {
//        return "index.html"; // 기본 페이지 반환
//    }

//    @PostMapping("/apply")
//    public ResponseEntity<?> regist(@RequestBody ApplyDTO apply) {
//
//        //데이터 등록
//        ApplyDTO savedApply = applyService.saveApply(apply);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(
//                new MediaType(
//                        "application",
//                        "json",
//                        Charset.forName("UTF-8")
//                ));
//
//        //등록 결과 응답
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("apply", savedApply);
//        ResponseMessage responseMessage = new ResponseMessage(
//                200, //create상태코드
//                "등록 성공!",
//                responseMap);
//
//        return new ResponseEntity<>(responseMessage, headers, HttpStatus.CREATED);
//    }


//@InitBinder
//public void initBinder(WebDataBinder binder) {
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    dateFormat.setLenient(false);
//    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//}

    @PostMapping("/apply")
    public ResponseEntity<?> regist(
            @RequestParam("date") String dateStr,
//            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date applyDate,
            @RequestParam("name") String name,
            @RequestParam("reason") String reason,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        // 문자열을 Date로 변환
        Date applyDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            applyDate = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "날짜 형식이 올바르지 않습니다."));
        }

        // 파일 처리 로직 추가
        String fileName = null;
        if (file != null && !file.isEmpty()) {
            String uploadDirPath = System.getProperty("user.dir") + "/uploads";
            File uploadDir = new File(uploadDirPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            try {
                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                String filePath = uploadDirPath + "/" + uniqueFileName;
                File dest = new File(filePath);
                file.transferTo(dest);

                fileName = uniqueFileName; // 파일 이름을 저장
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("message", "파일 저장 중 오류 발생: " + e.getMessage()));
            }
        }

        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setApplyDate(applyDate);
        applyDTO.setApplicant(name);
        applyDTO.setReason(reason);
        applyDTO.setFileName(fileName); // 파일 이름 설정

        ApplyDTO savedApply = applyService.saveApply(applyDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("apply", savedApply);
        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "등록 성공!",
                responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.CREATED);
    }

    @GetMapping("/apply")
    public ResponseEntity<ResponseMessage> apply() {

        //http 응답 헤더를 설정함
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType(
                        "application",
                        "json",
                        Charset.forName("UTF-8")
                ));

        //서비스에서 게시글 목록을 가져옴
        List<ApplyDTO> applys = applyService.getAllApplys();

        //응답에 포함할 데이터를 준비함
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("applys", applys);

        //응답메세지를 생성함
        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "조회 성공!",
                responseMap);

        //responseEntity를 생성하여 응답을 반환함
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @Operation(summary = "조회", description = "조회",
            parameters = {
                    @Parameter(
                            name="applyNo",
                            description = "사용자 화면에서 넘어오는 apply의 pk"
                    )
            })
    @GetMapping("/apply/{applyNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable Long applyNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType(
                        "application",
                        "json",
                        Charset.forName("UTF-8")
                ));

        //applys초기화
        List<ApplyDTO> applys = applyService.getAllApplys();

        if (applys == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(headers)
                    .body(new ResponseMessage(500, "서버 오류", null));
        }

        List<ApplyDTO> filteredApplys = applys.stream()
//                .filter(apply -> Object.equals(applyNo, apply.getApplyNo()))
                .filter(apply -> applyNo.equals(apply.getApplyNo()))  // Long타입 비교
                        .toList();

        if (filteredApplys.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(headers)
                    .body(new ResponseMessage(404, "조회 실패", null));
        }

        ApplyDTO foundApplys = filteredApplys.get(0);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("apply", foundApplys);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "조회성공", responseMap));
    }

    //수정
    @PutMapping("apply/{applyNo}")
    public ResponseEntity<?> modifyApply(@PathVariable Long applyNo, @RequestBody ApplyDTO modifyInfo){

        try {
            // 게시글 수정 서비스 메서드 호출
            applyService.UpdateApply(applyNo, modifyInfo);

            // 수정 후 새로운 리소스 위치를 나타내는 URI 반환
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(404, e.getMessage(),null));
        }

    }

    //삭제
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "정보삭제성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력된 파라미터")
    })
    @DeleteMapping("/apply/{applyNo}")
    public ResponseEntity<?> removeApply(@PathVariable Long applyNo) {

        try {
            // 게시글 삭제 서비스 메서드 호출
            applyService.deleteApply(applyNo);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            // 게시글이 없는 경우 404 상태 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//필터링기능
    @GetMapping("/apply/filtered")
    public ResponseEntity<ResponseMessage> apply(
            @RequestParam(required = false) Long applyNo,
            @RequestParam(required = false) String applicant,
            @RequestParam(required = false) String reason){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setContentType(
//                new MediaType(
//                        "application",
//                        "json",
//                        Charset.forName("UTF-8")
//                )
//        );

        //필터링된 결과 가져오기
        List<ApplyDTO> applys = applyService.findApplysByCriteria(applyNo, applicant, reason);

//        if(applyNo != null){
//            applys = applys.stream()
//                    .filter(apply -> applyNo.equals(apply.getApplyNo()))
//                    .toList();
//        }
//
//        if(applicant != null){
//            applys = applys.stream()
//                    .filter((apply -> applicant.equals(apply.getApplicant())))
//                    .toList();
//        }
//
//        if (reason != null) {
//            applys = applys.stream()
//                    .filter(apply -> reason.equals(apply.getReason()))
//                    .toList();
//        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("applys", applys);

        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "조회성공",
                responseMap);
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

}
