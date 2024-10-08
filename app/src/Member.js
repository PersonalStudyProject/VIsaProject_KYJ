import { useNavigate } from "react-router-dom";
import React, {useEffect, useState} from "react";
// import { UserContext } from "./Context";
import bcrypt from 'bcryptjs';  //bcrypt라이브러리 추가
import { v4 as uuidv4 } from 'uuid';
// import { verify } from "jsonwebtoken";

function Member(){

    const [name, setName] = useState(''); //이름
    const [username, setUsername] = useState(''); //아이디
    const [password, setPassword] =  useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const [email, setEmail] = useState('');
    const [verificationToken, setVerificationToken] = useState('');  //서버에서 발급한 인증
    const [enteredToken, setEnteredToken] = useState('');  //사용자 입력인증토큰
    const [isTokenSent, setIsTokenSent] = useState(false);  //인정번호 전송여부
    // const [savedUsers, setSavedUsers] = useState([]); //저장된 사용자 목록
    const [isVerified, setIsVerified] = useState(false); //인증성공여부
    const navigate =  useNavigate();
    // const {setUserData} = useContext(UserContext);
    // const nodemailer = require('nodemailer');

    //인증번호 전송함수
    const handleSendVerificationToken = async() => {
        if(!email){
            alert('이메일을 입력해주세요');
            return;
        }

        // const token = uuidv4();  //랜덤 인증번호 생성
        // setVerificationToken(token);  //인증번호 상태에 저장

        try{
            const response = await fetch('http://localhost:8080/send-verification-email',{
                method: 'POST',
                headers:{
                    'Content-Type': 'application/json',
                },
                // body: JSON.stringify({email, token}) //이메일과 인증번호 서버로 전송
                body: JSON.stringify({email})
            });

            if(response.ok){
                const serverToken = await response.text(); //서버에서 반환된 인증번호
                setVerificationToken(serverToken); //서버에서 받은 인증번호 저장
                setIsTokenSent(true);  //인증번호 전송 성공 상태 설정
                alert("인증번호가 이메일로 발송되었습니다");
            }else{
                console.error("인증번호 전송실패", response.statusText);
                alert("인증번호 전송에 실패했습니다. 다시 시도해주세요");
            }
        }catch(error){
            console.error('이메일 전송오류:', error);
            alert("이메일 전송중 오류가 발생했습니다");
        }
    };

    //인증번호 검증함수
    // const handleVerifyToken = () =>{
    //     console.log("Entered Token", enteredToken);
    //     console.log("Verification Token", verificationToken);
        
    //     if(enteredToken.trim() === verificationToken.trim()){
    //         alert("인증번호가 일치합니다");
    //         setIsVerified(true); //인증성공
    //     }else{
    //         alert("인증번호가 일치하지 않습니다");
    //         setIsVerified(false); //인증실패
    //     }
    // };

    //인증번호 검증함수
    const handleVerifyToken = async () => {
        console.log("Entered Token:", enteredToken); // 입력한 토큰 출력
        console.log("Verification Token:", verificationToken); // 저장된 인증번호 출력
    
        if (!email) {
            alert('이메일을 입력해주세요');
            return;
        }
    
        try {
            const response = await fetch('http://localhost:8080/verify-email-code', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, code: enteredToken }) // 이메일과 입력한 인증코드를 서버로 전송
            });
    
            if (response.ok) {
                const message = await response.text();
                alert(message); // 성공 메시지 출력
                setIsVerified(true); // 인증 성공 상태 설정
            } else {
                const errorMessage = await response.text();
                alert(errorMessage); // 에러 메시지 출력
                setIsVerified(false); // 인증 실패 상태 설정
            }
        } catch (error) {
            console.error('인증번호 검증 오류:', error);
            alert("인증번호 검증 중 오류가 발생했습니다");
        }
    };

    //컴포넌트가 마운트될때 기존회원리스트 가져오기
    useEffect(() => {
        const fetchMembers = async () => {
            const savedUsers = JSON.parse(localStorage.getItem('users')) || []; // 로컬 스토리지에서 사용자 목록 가져오기
            console.log(savedUsers);
            try {
                const response = await fetch("http://localhost:8080/members");
                if (!response.ok) throw new Error(response.statusText);
                    const data = await response.json();
                    console.log(data); // 응답 데이터를 로그로 확인
                    // setSavedUsers(data.members || []); // 응답에서 회원 리스트 설정
            } catch (error) {
                console.error("회원 리스트 가져오기 중 오류 발생", error);
            }
        };
        fetchMembers();
    }, []);

    useEffect(() => {
        console.log("isTokenSent:", isTokenSent);  // 상태 값 확인
    }, [isTokenSent]);

    const handleSubmit = async()=>{
        //비밀번호와 비밀번호 확인체크
        if(password !== passwordConfirm){
            alert('비밀번호가 일치하지 않습니다');
            return;
        }

            //기존 회원 리스트 가져오기
            const savedUsers = JSON.parse(localStorage.getItem('users')) || [];  //로컬스토리지에서 user배열가져옴
            console.log(savedUsers);  //현재 저장된 사용자 목록확인
            
            // 기존 사용자 중복 체크
            // if (!Array.isArray(savedUsers) || savedUsers.find(user=>user.username.toLowerCase()=== username.toLowerCase())){
            //     alert('이미 존재하는 아이디입니다.');
            //     // setSavedUsers([]); // 기본값으로 초기화
            //     return;
            // }
            if (Array.isArray(savedUsers) && savedUsers.find(user => user.username.toLowerCase() === username.toLowerCase())) {
                alert('이미 존재하는 아이디입니다.');
                return;
            }

            //기존 사용자 비밀번호 해싱
            const updatedUsers = await Promise.all(savedUsers.map(async(user)=>{
                if(user.password){
                    const hashedPassword = await bcrypt.hash(user.password, 10);
                    return{...user, password:hashedPassword}; //해시된 비밀번호로 업데이트
                }
                return user; //비밀번호가 없으면 그대로 반환
            }));

            //회원가입 정보 구성
            const hashedPassword = await bcrypt.hash(password, 10);
            const verificationToken = uuidv4();
            const newUser ={username, password: hashedPassword, email, verified:false, verificationToken};

            updatedUsers.push(newUser); //새사용자추가

            localStorage.setItem('users', JSON.stringify(updatedUsers));

            //서버에 사용자 정보 전송
            try{
                const response = await fetch("http://localhost:8080/api/members",{
                    method: "POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body: JSON.stringify(newUser)
                });

                if(response.ok){
                    console.log("회원정보가 백엔드에 성공적으로 저장되었습니다");
                    // sendVerificationEmail(email, verificationToken);
                    alert("회원가입이 완료되었습니다.");
                     //회원가입완료후 메인페이지로 이동
                    navigate('/');
                }else{
                    const errorText = await response.text();
                    console.error("회원정보저장중 오류발생",response.statusText, errorText);
                    alert("회원가입에 실패했습니다, 다시 시도해주세요");
                }
            }catch(error){
                console.error("백엔드로 데이터 전송중 오류발생", error);
                alert("서버와의 연결에 문제가 발생했습니다, 나중에 다시 시도해주세요")
            }
    };

    const sendVerificationEmail = async (email, token) => {
        try {
            await fetch('http://localhost:8080/send-verification-email', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, token })
            });
            setIsTokenSent(true); //상태변경
            console.log("이메일 전송 성공, isTokenSent 상태업데이트됨");  //상태변화로그
            alert("인증 이메일이 발송되었습니다.");
        } catch (error) {
            console.error('이메일 전송 실패:', error);
        }
    };

    // const handleVerifyToken = async () => {
    //     if (enteredToken === verificationToken) {
    //         // 인증 완료, 사용자 정보를 DB에 저장
    //         try {
    //             const response = await fetch(`http://localhost:8080/verify-token/${username}`, {
    //                 method: 'PATCH',
    //             });
    //             if (response.ok) {
    //                 alert("회원가입이 완료되었습니다.");
    //                 navigate('/');
    //             } else {
    //                 alert("인증 실패. 다시 시도해주세요.");
    //             }
    //         } catch (error) {
    //             console.error('인증 처리 중 오류:', error);
    //         }
    //     } else {
    //         alert("인증번호가 일치하지 않습니다.");
    //     }
    // };

    return(
        <>
        <h1>회원가입페이지입니다.</h1>
        <table border="0">
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>항목</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1.</td>
                        <td>이름</td>
                        <td>
                            <input 
                                value={name} 
                                onChange={(e) => setName(e.target.value)} 
                                placeholder="이름" 
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>2.</td>
                        <td>아이디</td>
                        <td>
                            <input 
                                value={username} 
                                onChange={(e) => setUsername(e.target.value)} 
                                placeholder="아이디" 
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>3.</td>
                        <td>비밀번호</td>
                        <td>
                            <input 
                                type="password" 
                                value={password} 
                                onChange={(e) => setPassword(e.target.value)} 
                                placeholder="비밀번호" 
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>4.</td>
                        <td>비밀번호 확인</td>
                        <td>
                            <input 
                                type="password" 
                                value={passwordConfirm} 
                                onChange={(e) => setPasswordConfirm(e.target.value)} 
                                placeholder="비밀번호 확인" 
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>5.</td>
                        <td>이메일</td>
                        <td>
                            <input 
                                value={email} 
                                onChange={(e) => setEmail(e.target.value)} 
                                placeholder="이메일" 
                            />
                        </td>
                        <td>
                        <button onClick={handleSendVerificationToken}>인증번호 요청</button>
                        </td>
                    </tr>
                    <tr>
                        <td>6.</td>
                        <td>인증번호 입력</td>
                        <td>
                        <input
                        value={enteredToken}
                        onChange={(e) => setEnteredToken(e.target.value)}
                        placeholder="인증번호를 입력하세요"
                    />

                        <button onClick={handleVerifyToken}>인증번호 확인</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        <br/>
        <button onClick={handleSubmit}>회원가입</button>
        <br/>
        <button onClick={()=>navigate('/')}>메인으로</button>
        </>
    );

}

export default Member;