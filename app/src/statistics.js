import axios from "axios";

function statistics(){
    axios.get('statistics/calculate')
    .then(Response => {
        const data = response.data;
        console.log("통계데이터 :", data);
    })
    .catch(error => {
        console.error("통계 데이터를 불러오는중 오류발생 :", error);
    });
}

export default statistics;