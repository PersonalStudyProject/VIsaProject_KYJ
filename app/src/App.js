import React from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Main from "./Main";
import Login from './Login'
import Etc from './Etc'
import Other from "./Other";
import Member from "./Member";
import { UserProvider } from "./Context";
import MyPage from "./MyPage";
import Apply from "./Apply";
import Cvisa from "./Visa/Cvisa"
import Dvisa from "./Visa/Dvisa"
import Evisa from "./Visa/Evisa"
import Fvisa from "./Visa/Fvisa"
import AdminPage from "./AdminPage";
import MemberInfo from "./Admin/MemberInfo";
import DetailC1 from "./Visa/DetailC1";
import DocumentApplication from "./DocumentApplication"
import Reservation from "./Reservation";

function App(){

return(
  <UserProvider>
  <Router>
    <Routes>
      <Route path="/" element={<Main/>}/>
      {/* <Route path="/Main" element={<Main/>}/> */}
      <Route path="/Login" element={<Login/>}/>
      <Route path="/Etc" element={<Etc/>}/>
      <Route path="/Other" element={<Other/>}/>
      <Route path="/Member" element={<Member/>}/>
      <Route path="/MyPage" element={<MyPage/>}/>
      <Route path="/Apply" element={<Apply/>}/>
      <Route path="/Visa/Cvisa" element={<Cvisa/>}/>
      <Route path="/Visa/DetailC1" element={<DetailC1 />} />
      <Route path="/Visa/Dvisa" element={<Dvisa/>}/>
      <Route path="/Visa/Evisa" element={<Evisa/>}/>
      <Route path="/Visa/Fvisa" element={<Fvisa/>}/>
      <Route path="/admin" element={<AdminPage/>}/>
      <Route path="/Admin/MemberInfo" element={<MemberInfo/>}/>
      <Route path="/DocumentApplication" element={<DocumentApplication/>}/>
      <Route path="/Reservation" element={<Reservation/>}/>
  </Routes>
</Router>
</UserProvider>
  );
}

export default App;