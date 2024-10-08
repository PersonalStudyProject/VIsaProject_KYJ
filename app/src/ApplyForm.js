import axios from 'axios';
import { useState } from 'react';

function ApplyForm() {
    const [formData, setFormData] = useState({
        applicant: '',
        document: '',
        reason: ''
    });

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/apply', formData)
            .then(response => {
                alert('Apply submitted successfully!');
            })
            .catch(error => {
                console.error('There was an error submitting the apply!', error);
            });
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" name="applicant" onChange={handleChange} placeholder="Applicant" />
            <input type="text" name="document" onChange={handleChange} placeholder="Document" />
            <input type="text" name="reason" onChange={handleChange} placeholder="Reason" />
            <button type="submit">Submit</button>
        </form>
    );
}

export default ApplyForm;