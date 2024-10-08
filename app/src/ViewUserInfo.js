import React, {useState, useEffect} from "react";

function ViewUserInfo(){
    const [userInfo, setUserInfo] =  useState([]);

    useEffect(()=>{
        const savedUsers = JSON.parse(localStorage.getItem('users'))||[];
        setUserInfo(savedUsers);
    },[]);

    return(
        <div>
            <h1>회원정보</h1>
            <ul>
                {userInfo.map((user, index)=>(
                    <li key={index}>
                        <p>Username:{user.username}</p>
                        <p>Email:{user.email}</p>
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default ViewUserInfo;