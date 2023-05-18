import { Link, useLocation } from "react-router-dom";
import { useState, useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { PATH } from '../../utils/paths';

import { Card } from 'antd';
import Modal from "react-modal";
import axios from "axios";
import moment from 'moment';

import "../../styles/css/Qna/qna_logdetail.css";

type QNADETAIL = {
    "commentid": string,
    "commentSeq": string,
    "content": string,
    "commentDate": string,
    "replyStatus": string,
    "userId": string,
    "inquiryId": string

}

function QnaLogDetail() {
    const location = useLocation();

    const id = location.state.Qna;
    const content = location.state.Content;
    const title = location.state.Title;

    const [qnaDetail, setQnaDetail] = useState<QNADETAIL[]>([]);
    const [openModal, setOpenModal] = useState(false);
    const [comment, setComment] = useState("");
    const userid = "1";
    const accessToken = 1;

    const handleModal = () => {
        setOpenModal(true);
    };

    const handleRegister = async () => {
        try{
        await axios.post(PATH.SERVER + `api/v1/inquiries/${id}/comments`, 
        {
            content: comment,
            userId: userid,
            inquiryId : id
        },
        {
            headers:{'Authorization':`Bearer ${accessToken}`},
        }).then(function(response){
            console.log(response);
        }).catch(function(error){
            console.log(error);
        })
        setOpenModal(false);
        }
        catch{
        }
    };

    const getQnaDetail = () => {
        axios.get<QNADETAIL[]>(PATH.SERVER + `api/v1/inquiries/${id}/comments`, {
            headers:{
            'Authorization':`Bearer ${accessToken}`
            }
        }).then(response => {
            //console.log(typeof response.data[userid]);
            setQnaDetail(response.data);
        }).catch(function(error){
            console.log(error)
        })
    };

    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
      setHeaderTitle('문의 상세 내역');
      getQnaDetail();
      console.log("loaded");
    }, [openModal]);

    return(
            <div className = "main-page">

                <div>
                    <Card title={title} className="mainBox">
                        <Card>{content}</Card>
                        {qnaDetail.map((answer) => (
                            
                            <Card title={<span style={answer.userId==userid ? {color: 'orange'} : {color: 'red'}}>{answer.userId==userid ? "문의내용" : "답변"}</span>} extra={moment(answer.commentDate).format('YYYY-MM-DD HH:mm')}  key={answer.commentid} className="detailBox">
                                <p>{"content : " + answer.content}</p>

                            </Card>
                        ))}
                    </Card>
                </div>

                <div>
                    <Link to="/qna/main" ><button className="beforeButton">이전</button></Link>
                    <button onClick={handleModal} className="nextButton">추가 문의</button>
                    <Modal isOpen={openModal} onRequestClose={() => setOpenModal(false)} className="modalImage">
                        <div className="inputGrid">
                            {/*<input placeholder="문의 제목 입력" className="inputTitle"></input>*/}
                            <textarea onChange={e => setComment(e.target.value)} placeholder="문의 내용 입력" className="inputDetail"></textarea>
                        </div>
                        <button onClick={()=>setOpenModal(false)} className="modalClose">&#10005;</button>
                        <button onClick={()=>handleRegister()} className="registerModal">등록</button>

                    </Modal>
                </div>
            </div>
        
    );

}

export default QnaLogDetail;