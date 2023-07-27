import React, { Component } from 'react';
import S1 from './MulExam';
import S2 from './RankServer';
import RankTable from './RankTable';

class Main extends Component {
    constructor(props) {
        super(props);
        this.state={
            mainRefresh :'',
            completed: 0
        }
    }
  
    
    render() {
        return (
            <div>
                {/* <S1 /> */}
                {/* <S2/> */}
                <RankTable />
            </div>
        );
    }
}

export default Main;