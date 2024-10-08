import React, {useContext, useState, useEffect} from "react";
import { UserContext } from "./Context";
import { useNavigate } from "react-router-dom";

function MyPage(){

    const {userData, setUserData} = useContext(UserContext);
    const [ username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const navigate =  useNavigate();

    useEffect(()=>{
        //페이지가 로드될때 localstorage에서 사용자 정보 가져오기
        const currentUser =  JSON.parse(localStorage.getItem('currentUser'));
        if(currentUser){
            setUsername(currentUser.username);
            setPassword(currentUser.password);
            setEmail(currentUser.email ||''); //이메일이 없으면 빈 문자열
        }else if(userData.username){
            setUsername(userData.username);
            setPassword(userData.password);
            setEmail(userData.email||'');
        }
    },[userData]);

    // useEffect(()=>{
    //     setUsername(userData.username);
    // },[userData.username]);

    const handleUpdate = () =>{
        const updateUser = { username, password, email};

        //localstorage에 업데이트한 사용자 정보 저장
        localStorage.setItem('currentUser', JSON.stringify(updateUser));
        setUserData(updateUser);

        alert("정보가 업데이트 되었습니다");
    };

    const handleLogout = () =>{
        localStorage.removeItem('currentUser');
        setUserData({username:'', password:'', email:''});
        navigate('/Login');
    }

    return(
        <div>
            <h1>마이페이지</h1>
            <p>현재 로그인한 사용자:{userData.username}</p>
            <input type="text" value={username}
            onChange={(e)=> setUsername(e.target.value)}
            placeholder="아이디"/>
            <br/>

            <input type="password" value={password}
            onChange={(e)=> setPassword(e.target.value)}
            placeholder="비밀번호"/>
            <br/>

            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="이메일 (선택)"/>
            <br />

            <button onClick={handleUpdate}>정보업데이트</button>
            <button onClick={handleLogout}>로그아웃</button>
        </div>
    );

}

export default MyPage;