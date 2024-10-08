import axios from 'axios';
import { useEffect, useState } from 'react';

function ApplyList() {
    const [applys, setApplys] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/apply')
            .then(response => {
                setApplys(response.data.applys);
            })
            .catch(error => {
                console.error("There was an error fetching the apply list!", error);
            });
    }, []);

    return (
        <div>
            <h1>Apply List</h1>
            <ul>
                {applys.map(apply => (
                    <li key={apply.applyNo}>{apply.applicant}: {apply.reason}</li>
                ))}
            </ul>
        </div>
    );
}

export default ApplyList;