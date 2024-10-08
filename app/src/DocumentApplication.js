import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function DocumentApplication() {
    const navigate = useNavigate();
    const [selectedFile, setSelectedFile] = useState(null);
    const [date, setDate] = useState(""); 
    const [name, setName] = useState("");
    const [reason, setReason] = useState("");

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);  // 선택된 파일을 상태로 저장
    };

    //파일업로드
    const handleFileUpload = async (event) => {
        event.preventDefault();
        // setSelectedFile(event.target.files[0]); //선택된 파일을 상태로 저장

        if (selectedFile) {
            const formData = new FormData();
            formData.append("file", selectedFile);
    
            try {
                const response = await fetch("http://localhost:8080/upload", {
                    method: "POST",
                    body: formData,
                });
    
                let data;
                if (response.ok) {
                    const contentType = response.headers.get("Content-Type");
                    if (contentType && contentType.includes("application/json")) {
                        data = await response.json();
                    } else {
                        data = await response.text(); // JSON이 아닌 경우 텍스트로 처리
                    }
                    alert("파일 업로드 성공");
                    console.log("파일 업로드 성공:", data);
                } else {
                    alert("파일 업로드 실패");
                    console.error("파일 업로드 실패:", response.statusText);
                }
            } catch (error) {
                console.error("파일 업로드 에러:", error.message);
                console.error("파일 업로드 스택 트레이스:", error.stack);
                alert("파일 업로드 중 오류가 발생했습니다.");
            }
        } else {
            alert("파일을 선택하세요");
        }
    };

    // 신청하기
    const handleFinalSubmit = async (event) => {
        event.preventDefault();

         // 날짜가 유효한 Date 객체인지 확인
    const formattedDate = date instanceof Date ? date.toISOString().split('T')[0] : date; // 'YYYY-MM-DD' 형식으로 변환
        
        if (formattedDate && name && reason) {
            const formData = new FormData();
            formData.append("date", formattedDate); // ISO 문자열로 변환 후 날짜 부분만 추출
            // formData.append("date", date);
            formData.append("name", name);
            formData.append("reason", reason);
            if (selectedFile) {
                formData.append("file", selectedFile);
            }

            try {
                const response = await fetch("http://localhost:8080/apply", {
                    method: "POST",
                    body: formData,
                });

                if (response.ok) {
                    const data = await response.json();
                    alert("신청 완료!");
                    console.log("신청 완료:", data);
                } else {
                    alert("신청 실패.");
                    console.error("신청 실패:", response.statusText);
                }
            } catch (error) {
                console.error("신청 중 오류 발생:", error);
                alert("신청 중 오류가 발생했습니다.");
            }
        } else {
            alert("모든 필드를 입력하세요.");
        }
    };

    return (
        <>
            <br />
            <button onClick={() => navigate('/')}>메인으로</button><br /><br />
            <button onClick={() => navigate(-1)}>뒤로가기</button>

            <h1>서류 신청 페이지</h1>
            <form onSubmit={handleFileUpload}>
                <label>
                    신청 날짜:
                    <input type="date" value={date}
                        onChange={(e) => setDate(e.target.value)} required/>
                </label>
                <br/>
                <label>
                    이름:
                    <input type="text" value={name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder="이름을 입력하세요" required/>
                </label>
                <br />
                <label>
                    사유:
                    <input type="text" value={reason}
                        onChange={(e) => setReason(e.target.value)}
                        placeholder="연장 사유를 입력하세요" required/>
                </label>
                <br />
                <input type="file" onChange={handleFileChange} />
                <button type="submit">파일 업로드</button>
            </form>
            <br />
            <button onClick={handleFinalSubmit}>신청하기</button>
        </>
    );
}

export default DocumentApplication;

