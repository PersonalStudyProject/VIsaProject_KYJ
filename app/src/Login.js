import React,{useContext, useEffect, useState} from "react";
import { UserContext } from "./Context";
import { useNavigate } from "react-router-dom";
import bcrypt from 'bcryptjs';

function Login(){

    const {setUserData} = useContext(UserContext);
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    //로컬스토리지에서 유저정보를 가져와서 초기설정
    useEffect(()=>{

        //기존 저장된 회원리스트 가져오기
        const savedUsers = JSON.parse(localStorage.getItem('users')) || [];
        console.log('저장된 회원리스트', savedUsers); //회원리스트출력

        //관리자 계정이 이미 존재하는지 확인(대소문자 구분없이)
        const adminExists = savedUsers.some(user => user.username.toLowerCase()=== 'admin');

    // 관리자 계정이 없을 때만 추가
    if (!adminExists) {
        const salt = bcrypt.genSaltSync(10); //암호화용 솔트생성
        const hashedPassword = bcrypt.hashSync('admin123', salt);  //비밀번호 암호화
        console.log(hashedPassword); //해시된비밀번호확인

        //해시된값이 로컬스토리지에 저장
        const updatedUsers = [...savedUsers, { username: 'admin', password: hashedPassword}];
        
        // 로컬스토리지에 업데이트된 사용자 리스트 저장
        localStorage.setItem('users', JSON.stringify(updatedUsers)); 
        
        // 회원가입 완료 메세지
        alert('관리자 계정이 추가되었습니다.');
    }

    },[]); //[]안에 아무 의존성도 없으므로 컴포넌트가 처음 마운트될떄 한번만 실행됨

    const handleLogin = async() =>{

        //저장된 회원리스트 가져오기
        const savedUsers = JSON.parse(localStorage.getItem('users')) || [];

        //입력한 유저네임과 비밀번호에 해당하는 사용자 찾기
        const foundUser = savedUsers.find(user => 
            user.username.toLowerCase() === username.toLowerCase() 
        );

        if (foundUser){
            const inputPassword = password.trim();  //비밀번호 공백제거
            if(typeof password !== 'string' || typeof foundUser.password !== 'string'){
                console.error("비밀번호가 문자열이 아니거나 잘못된 형식입니다.");
                return;
            } 

            //입력한 비밀번호와 저장된 해시된 비밀번호 비교
            const isPasswordVaild = await bcrypt.compare(inputPassword, foundUser.password);
            console.log(isPasswordVaild);  //true/false로 비밀번호가 일치하는지 확인
            console.log(foundUser.password);
            console.log(password.trim());
            
            if(isPasswordVaild){
                //로그인성공시 usercontext와 localstoryage없데이트
                // setUserData({ username: foundUser.username, password: foundUser.password });
                setUserData({ username: foundUser.username});
                localStorage.setItem('currentUser', JSON.stringify(foundUser));
            
            //관리자 로그인 
                if(foundUser.username.toLowerCase() === 'admin'){
                    alert(`${foundUser.username} 관리자님 , 로그인 성공`);
                    navigate('/admin');
                }else{
                    //로그인성공
                    alert(`${foundUser.username} 님 , 로그인 성공`);
                    navigate('/');
                }
        }else{
            alert('아이디나 비밀번호가 일치하지 않습니다');
        }
    };

    };

    return(
        <>
        <h1>로그인페이지</h1>
        <img src="/image/profile.PNG" alt="Profile" />
        <br/><br/>
        <input placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)}/>
        <br/>
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}/>
        <br/><br/>
        <button onClick={handleLogin}>로그인</button>
        <br/> <br/>

        <a href="Member">회원가입</a>
        <button onClick={()=>navigate('/')}>메인으로</button>
        </>
    );
}

export default Login;