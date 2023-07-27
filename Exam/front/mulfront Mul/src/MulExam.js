import React, { useState } from 'react';
import { Button, Form, Input } from "antd";
import axios from 'axios';

function S1() {

  // console.log(props.mainState);
  const [firstNumber, setFirstNumber] = useState('');
  const [secondNumber, setSecondNumber] = useState('');
  const [resultNumber, setResultNumber] = useState('');
  const [examKey, setExamKey] = useState('');
  const [name, setName] = useState('');
  const [showResult, setShowResult] = useState(false);

  const handleFirstNumberChange = (e) => {
    setFirstNumber(e.target.value);
  };

  const handleSecondNumberChange = (e) => {
    setSecondNumber(e.target.value);
  };

  const handleResultNumberChange = (e) => {
    setResultNumber(e.target.value);
  };

  const handleNameChanege = (e) => {
    setName(e.target.value);
  };


  const s1getData = (e) => {
    e.preventDefault();
    console.log("s1getData");
    const s1Data = {
      firstnumber: firstNumber,
      secondnumber: secondNumber,
    }
    //구구단 s1값 받기
    axios.get('http://192.168.0.107:8081/exam', { params: s1Data })
      .then((res) => {
        console.log('s1Get성공 : ', res.data);
        setExamKey(res.data.key);
        setFirstNumber(res.data.exam[0].toString());
        setSecondNumber(res.data.exam[1].toString());
        setShowResult(false);
      })
  }


  const s1ResultData = () => {
    console.log("----", name, resultNumber, examKey);
    axios.post('http://192.168.0.107:8081/exam', {
      'name': name,
      'result': resultNumber,
      'examKey': examKey,
    })
      .then((response) => {
        console.log('s1결과값 Post 성공:', response.data);
        setShowResult(true); // 정답 전송 시 폼을 보여줌
        clearInputs();
      })
      .catch((err) => {
        console.log('s1결과값 Post 실패:', err);
        setShowResult(true); // 오답 전송 시 폼을 보여줌
        clearInputs();
      });
      window.location.reload();
  };

  //구구단 s1 결과값 버튼 눌렀을때 내용 초기화
  const clearInputs = () => {
    setFirstNumber('');
    setSecondNumber('');
    setExamKey('');
    setName('');
    setResultNumber('');
    setShowResult(false);
  }
  

  return (
    <div className='s1'>
        <h1>구구단 게임</h1>
        <Form>
        <Form.Item label="이름">
          <Input value={name} onChange={handleNameChanege} placeholder="이름을 입력하세요" style={{ width: '30%' }} />
        </Form.Item>
        <Form.Item label="두 숫자">
          <Input value={firstNumber} disabled style={{ width: '40px' }} />
          X
          <Input value={secondNumber} disabled style={{ width: '40px' }} />
        </Form.Item>
        <Form.Item label="정답">
          <Input value={resultNumber} onChange={handleResultNumberChange} placeholder="정답을 입력하세요" style={{ width: '30%' }} />
        </Form.Item>

        <Button type="primary" htmlType="문제 가져오기" onClick={e => { s1getData(e) }}>
          문제 가져오기
        </Button>
        <Button type="primary" onClick={s1ResultData}>
          정답전송
        </Button>
        </Form><br/>


      {showResult && (
        <div>
          {resultNumber === examKey ? (
            <h2>정답입니다!</h2>
          ) : (
            <h2>오답입니다!</h2>
          )}
        </div>
      )}
    </div>
  );
}

export default S1;