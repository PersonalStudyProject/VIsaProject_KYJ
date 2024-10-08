import React, {useState, useEffect} from "react";

function FileList(){
    const [files, setFiles] = useState([]);

    useEffect(()=>{
        const fetchFiles = async() =>{
            try{
                const response = await fetch("http://localhost:5000/files");
                const data = await response.json();
                setFiles(data.files);
            }catch(error){
                console.log("파일목록 가져오기 실패 :", error);
                
            }
        };
        fetchFiles();
    },[]);

    return(
        <div>
            <h1>업로드된 파일목록</h1>
            <ul>
                {files.map((file, index)=>(
                    <li key={index}>
                        <a href={`http://localhost:5000/uploads/${file}`} target="_blank" rel="noopener noreferrer">
                        {file}
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    )

}

export default FileList;