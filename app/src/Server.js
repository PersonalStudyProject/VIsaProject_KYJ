// require('dotenv').config();

const express = require("express");
const multer = require("multer");
const path = require("path");
const fs = require('fs');
const axios = require('axios'); // 추가: HTTP 요청을 위해 axios 사용
const nodemailer = require("nodemailer");
const app = express();
const cors = require("cors");
// const { CLIENT_RENEG_LIMIT } = require("tls");
// const { default: Reservation } = require("./Reservation");
app.use(cors());
app.use(express.json()); // JSON 데이터 처리를 위한 미들웨어 추가

//사용자 등록 앤드포인트
app.post("/api/members", async(req, res)=>{
    const {username, password, email, verified, verificationToken} = req.body;

    //인증 이메일 전송
    sendVerificationEmail(email, verificationToken);

    res.status(201).json({ message: "회원가입 완료"});
});

const nodemailer = require('nodemailer');

//이메일 전송설정
const transporter = nodemailer.createTransport({
    service:"gmail", //사용할 이메일 서비스
    auth:{
        user: "yeonjinKn.n@gmail.com",
        pass: "sfnkfnktnwfnkldu",
    }
});

//이메일 전송함수
const sendVerificationEmail = (to, verificationToken) =>{
    const mailOptions ={
        from: 'yeonjinKn.n@gmail.com',
        to,
        subject:'회원가입 인증이메일',
        text: `인증링크: http://localhost:3000/verify?token=${verificationToken}`,  //실제 url로 수정
    };

    transporter.sendMail(mailOptions, (error, info)=>{
        if(error){
            console.error('이메일전송실패 :', error);
        }else{
            console.log('이메일 전송성공 : ', info.response);
            
        }
    });
};


// 정적 파일 서빙 설정 (예: public 폴더에 favicon.ico 파일이 있을 때)
app.use(express.static(path.join(__dirname, 'public')));
// 또는 정적 파일을 루트 경로에서 제공
app.use('/favicon.ico', express.static(path.join(__dirname, 'public', 'favicon.ico')));
// app.use('/uploads', express.static('uploads'));

// 파일 저장 위치 및 이름 설정
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        if (!fs.existsSync('uploads')) {
            console.log('uploads 폴더가 없으므로 생성합니다.');
            fs.mkdirSync('uploads');
        }
        cb(null, "uploads/");
    },
    filename: (req, file, cb) => {
        console.log('업로드 된 파일 이름', file.originalname);
        cb(null, Date.now() + path.extname(file.originalname));
    },
});

const upload = multer({ storage: storage });

// 정적 파일 서빙을 위한 추가
app.use('/uploads', express.static('uploads'));

// 파일 업로드 엔드포인트
app.post("/upload", upload.single("file"), (req, res) => {
    try {
        console.log('파일 업로드 성공: ', req.file);
        res.json({ message: "파일 업로드 성공", file: req.file });
    } catch (error) {
        console.log('파일 업로드 실패: ', error);
        res.status(500).json({ message: "파일 업로드 실패" });
    }
});

// 파일 목록을 반환하는 엔드포인트
app.get('/files', (req, res) => {
    fs.readdir('uploads', (err, files) => {
        if (err) {
            return res.status(500).json({ message: '파일 목록 가져오기 실패' });
        }
        res.json({ files });
    });
});

// 예약 데이터 처리 엔드포인트 추가
app.post('/Reservation', async(req, res) => {
    console.log('reservation요청이 들어왔습니다', req.body);
    
    const { name, date, time, memberId } = req.body;
    console.log('받은 예약데이터:', req.body); //데이터 확인용
    

    const data ={
        memberId,
        reservationName: name,
        reservationDate: date,
        reservationTime: time
};
    // 예약 데이터 처리 로직 추가
    // console.log(`예약 데이터 - 이름: ${name}, 날짜: ${date}, 시간: ${time}`);
    // console.log('전송할데이터:', {name, date, time});
    
     // 8080번 스프링 서버로 예약 데이터를 전달하는 로직 추가
    try {
        // const response = await axios.post('http://localhost:8080/api/Reservation', { name, date, time });
        const response = await axios.post('http://localhost:8080/api/Reservation', data);

        if (response.status === 201) { // 스프링 서버에서 성공적으로 처리된 경우
            res.status(201).json({ message: '예약이 성공적으로 8080번 서버에 전달되었습니다!' });
        } else {
            console.error('서버응답상태', response.status);
            
            res.status(response.status).json({ message: '스프링 서버에서 예약 처리 실패' });
        }
    } catch (error) {
        if(error.response){
        console.error('8080번 서버로 전송 중 에러:', error.response.data);
        res.status(error.response.status).json({message:error.response.data});
        }else if(error.request){
            console.error('요청에러', error.request);
            res.status(500).json({message: '요청이 서버로 전송되지 않았습니다'});
        }else{
            console.error('8080번 서버로 전송중 에러', error.message);
            res.status(500).json({ message: '8080번 스프링 서버로의 전송 중 오류 발생' +error.message});
        }
        }
    }

    // 성공적으로 처리된 경우 응답
    // res.status(200).json({ message: '예약이 성공적으로 완료되었습니다!' });
);

// 회원가입 엔드포인트
app.post("/signup", async (req, res) => {
    const { email } = req.body;

    // 인증 링크 생성 (예: http://localhost:5000/verify?token=abc123)
    const token = Math.random().toString(36).substring(2); // 임시 토큰 생성
    const verifyLink = `http://localhost:5000/verify?token=${token}`;

    // 이메일 내용
    const mailOptions = {
        from: "yeonjinKn.n@gmail.com",
        to: email,
        subject: "이메일 인증",
        text: `아래 링크를 클릭하여 이메일 인증을 완료하세요: ${verifyLink}`,
    };

    //이메일 전송코드(이메일 서비스 설정 필요)
    try {
        await saveTokenToDatabase(email, token); //토큰과 이메일을 데이터 베이스에 저장

        //이메일 전송코드(이메일 서비스 설정 필요)
        await transporter.sendMail(mailOptions);
        res.status(200).json({ message: "인증 이메일이 전송되었습니다." });
    } catch (error) {
        console.error("이메일 전송 실패:", error);
        res.status(500).json({ message: "이메일 전송 실패" });
    }
});

// 인증 처리 엔드포인트
app.get("/verify", async(req, res) => {
    const { token } = req.query;

    //데이터베이스에서 토큰을 확인
    const user = await findUserByToken(token);

    if (user) {
        //이메일 인증처리
        user.isVerified = true;
        await user.save();
        res.send("이메일 인증이 완료되었습니다!");
    } else {
        res.status(400).send("유효하지 않은 토큰입니다.");
    }
});

// 루트 경로 처리
app.get("/", (req, res) => {
    res.send("서버가 정상적으로 실행 중입니다!");
});

// 서버 실행
app.listen(5000, () => {
    console.log("서버가 5000번 포트에서 실행 중입니다.");
});

//가상의 데이터베이스 저장함수 예시
const saveTokenToDatabase = async(email, token) =>{
    //데이터베이스에 토큰과 이메일을 저장하는 로직구현
};

const findUserByToken = async(token) =>{
    //데이터베이스에서 토큰을 기방으로 사용자를 찾는 로직구현
};