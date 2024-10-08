import React from "react";
import { useState, useEffect} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';

function Etc(){

    const location = useLocation();
    const navigate = useNavigate();
    // const [savedValue, setSavedValue] = useState('');
    const [inputList, setInputList] = useState([]);
    const [searchTerm, setSearchTerm] = useState(''); //검색어 상태추가
    const [filteredList, setFilteredList] = useState([]); //필터링된 리스트상태추가
    const [editIndex, setEditIndex] = useState(null); //수정중인 항목의 인덱스
    const [editValue, setEditValue] = useState(''); //수정중인 값

    // useEffect(()=>{
    //     if(location.state?.inputValue){
    //         setSavedValue(location.state.inputValue); //전달 받은 값 저장
    //     }
    // },[location.state]);

    useEffect(()=>{
        console.log('location state', location.state);
        if(location.state?.inputList){
        setInputList(location.state.inputList);
        setFilteredList(location.state.inputList); // 초기값을 inputList로 설정
        }else{

            const savedList = localStorage.getItem('inputList');
            if(savedList){
                const parsedList = JSON.parse(savedList);
                setInputList(parsedList);
                setFilteredList(parsedList);
            }
        }
    },[location.state]);

    const goToMain = () => {
        navigate('/');
    }

    const deleteItem =(indexToDelete) =>{
        //inputlist에서 해당항목삭제
        const newList =  inputList.filter((_, index)=> index !== indexToDelete);
        setInputList(newList);
        //localstorage에 업데이트된 리스트 저장
        localStorage.setItem('inputList', JSON.stringify(newList));

        //검색필터 다시 적용
        const newFilteredList =  newList.filter(item => item.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredList(newFilteredList);

        alert('항목이 삭제되었습니다.');

    }

    const handleSearch = () => {
        // 검색어를 기준으로 필터링된 리스트 생성
        const newFilteredList = inputList.filter(item => item.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredList(newFilteredList);
    };

    const enableEdit =(index, value) =>{

        setEditIndex(index); //수정할 항목의 인덱스를 저장
        setEditValue(value); //수정할 항목의 현재 값을 저장
    };

    const saveEdit = (index) =>{
        const newList = [...inputList];
        newList[index] = editValue; //수정된값을 리스트에 반영
        setInputList(newList);
        setFilteredList(newList);
        localStorage.setItem('inputList', JSON.stringify(newList));

        setEditIndex(null); //수정 모드 종료
        setEditValue('');  //수정중인 값 초기화
    }

return(
    <div>
    <h1>기타</h1>
    {/* 검색어 상태 업데이트 */}
    <input type="text" placeholder="검색어를 입력하세요" value={searchTerm}
    onChange={(e)=> setSearchTerm(e.target.value)}/> 

    <button onClick={handleSearch}>검색</button>
    <br/><br/>
    {/* {savedValue ? <p>저장된값:{savedValue}</p>:<p>저장된값이 없음</p>} */}
    {filteredList.length>0 ? (
        <table border="1">
            <thead>
                <tr>
                    <th>입력값</th>
                </tr>
            </thead>
            <tbody>
            {filteredList.map((value, index) => (
                            <tr key={index}>
                                <td>
                                    {editIndex === index ? (
                                        <input 
                                            type="text" 
                                            value={editValue} 
                                            onChange={(e) => setEditValue(e.target.value)} 
                                        />
                                    ) : (
                                        value
                                    )}
                                </td>
                                <td>
                                    {editIndex === index ? (
                                        <button onClick={() => saveEdit(index)}>저장</button>
                                    ) : (
                                        <button onClick={() => enableEdit(index, value)}>수정</button>
                                    )}
                                    <button onClick={() => deleteItem(index)}>삭제</button>
                                </td>
                            </tr>
                        ))}
            </tbody>
        </table>
    ):(<p>저장된 값이 없음</p>

    )}
    <br/>
    <button onClick = {goToMain}>메인으로 가기</button>
    
    </div>
);

}

export default Etc;