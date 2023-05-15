import React from "react";

import { Link, useLocation } from "react-router-dom";
import { useState, useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import {PATH} from '../../utils/paths';

import "./transfer_amount.css";
import Modal from 'react-modal';
import axios from "axios";
Modal.setAppElement('#root');

function TransferAmt() {
    const location = useLocation();
    const account = location.state.account;
    const name = location.state.owner;
    const balance = 1000;
    const accessToken = 1;

    const [amount, setAmount] = useState(0);

    
    const [openModal, setOpenModal] = useState(false);
    /*const [pwModal, setPwModal] = useState(false);*/

    const [error, setError] = useState("");
    const [errorModal, setErrorModal] = useState(false);

    const handleNext = (amount : number) =>{
        if(amount===0 || isNaN(amount) || amount > balance){
            setError("금액을 다시 확인해주세요.")
            setErrorModal(true);
        }
        else{
            setOpenModal(true);
            setError("");
        }
    };

    const addValue = (adder : number) =>{
        //console.log(isNaN(amount) || amount==="");
        setAmount(amount + adder);
    };


    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
      setHeaderTitle('금액 입력');
      
    });

    return(
        <div>
                
                <div>
                    <div className="accountInfo">
                        <span className="fontGrey">받는 분</span>
                        <span className="fontBlack">{name}<span className="fontGrey">님</span></span>
                        <span className="fontOrange">{account}</span>
                    </div>

                    <div>
                        <input type="number" onChange={(e:React.ChangeEvent<HTMLInputElement>) => setAmount(+e.target.value)} placeholder="금액 입력"
                         style={amount===0 ? {color:"black"} : {color:"C7C7C7"}} value={amount===0 ? "" : amount} className="inputBox"></input>
                        <span className="fontTP">TP</span>
                        
                        <div className="incButton">
                            <button onClick={()=>addValue(10)} value={10}>+10</button>                      
                            <button onClick={()=>addValue(30)} value={30}>+30</button>
                            <button onClick={()=>addValue(60)} value={60}>+60</button>
                        </div>
                    </div>

                </div>

                <div>
                <button onClick={() => handleNext(amount)} className="nextButton">다음</button>

                <Modal isOpen={errorModal} onRequestClose={() => setErrorModal(false)} className="modalError">
                    <span className="fontErrConfirm">{error}</span>
                    <button onClick={()=>setErrorModal(false)} className="errorConfirm">확인</button>
                </Modal>

                <Modal isOpen={openModal} onRequestClose={() => setOpenModal(false)} className="modalImage">
                    <div className="modalInfo">
                        <p className="fontGrey">받는 분</p>
                        <span className="fontBlack">{name} <span className="fontGrey">님</span></span>
                        <span className="fontOrange">{account}</span>
                        <br></br><br></br>
                        <p className="fontGrey">보내는 TIME PAY</p>
                        <span className="fontBlack">{amount}</span>
                        <span className="fontOrange">TP</span>
                    </div>
                    <button onClick={()=>setOpenModal(false)} className="modalClose">&#10005;</button>
                    <button onClick={()=>setOpenModal(false)} className="beforeModal">이전</button>
                    <Link to="/transfer/password" state={{account : account, amount : amount, name : name}}>"<button onClick={()=>setOpenModal(false)} className="nextModal">다음</button></Link>
                </Modal>

                <Link to="/transfer/account" ><button className="beforeButton">이전</button></Link>
                </div>
            </div>

    );


}

export default TransferAmt;