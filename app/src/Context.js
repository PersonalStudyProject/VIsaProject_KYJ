import React, { createContext, useState } from "react";

export const UserContext = createContext();

export const UserProvider =({children}) => {
    const [userData, setUserData] = useState({
        // username : localStorage.getItem('username')||'',
        // password: localStorage.getItem('password')||''
    
        username: localStorage.getItem('currentUser') ? JSON.parse(localStorage.getItem('currentUser')).username : '',
        password: localStorage.getItem('currentUser') ? JSON.parse(localStorage.getItem('currentUser')).password : ''

    });

    const logout = () =>{
        setUserData({username:'', password:''});
        // localStorage.removeItem('username');
        // localStorage.removeItem('password');
        localStorage.removeItem('currentUser');
    }

    return(
        <UserContext.Provider value={{userData, setUserData, logout}}>
            {children}
        </UserContext.Provider>
    );
}