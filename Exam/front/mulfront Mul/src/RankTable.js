import React, { Component } from 'react';
import { Table } from "antd";
import columns from "./Columns";
import axios from "axios";

class RankTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            RankData : []
        }
    }
    componentDidMount(){
        axios.get("http://192.168.0.69:8080/leaderBoard/get").then(
            (res)=>{
                this.setState({RankData : res.data})
            }
        )
    }

    // onChange = (pagination, filters, sorter, extra) => {
    //     console.log('params', pagination, filters, sorter, extra);
    //   };
    render() {
        return (
            <>
              <Table columns={columns} dataSource={this.state.RankData}  />; 
              {/* onChange={this.onChange} */}
            </>
        );
    }
}

export default RankTable;

