import React, { useState } from 'react';
import axios from 'axios';
import { Table } from 'antd';

function S2() {
    const [name, setName] = useState('');
    const [rank, setRank] = useState('');
    const [score, setScore] = useState('');
  
    const handleNameChange = (e) => {
      setName(e.target.value);
    };
    const handleRankChange = (e) => {
      setRank(e.target.value);
    };
    const handleScoreChange = (e) => {
      setScore(e.target.value);
    };
  
    const s2getData = () => {
      const url = `http://192.168.0.69:8080/exam?name=${name}&rank=${rank}&score=${score}`;
      axios.get(url)
        .then((res) => {
          console.log('데이터 가져오기 성공:', res.data);
          // 서버에서 가져온 데이터를 상태 변수에 업데이트
          setName(res.data.name);
          setRank(res.data.rank);
          setScore(res.data.score);
        })
        .catch((error) => {
          console.log('데이터 가져오기 실패:', error);
        });
    };
  
    return (
      <div className='s1'>
        <h1>구구단 게임</h1>
        <Table>
          <thead>
            <tr>
              <th>#</th>
              {Array.from({ length: 12 }).map((_, index) => (
                <th key={index}>Table heading</th>
              ))}
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
              {Array.from({ length: 12 }).map((_, index) => (
                <td key={index}>Table cell {index}</td>
              ))}
            </tr>
            {/* Add more table rows here */}
          </tbody>
          </Table>
      </div>
    );
  }


export default S2;