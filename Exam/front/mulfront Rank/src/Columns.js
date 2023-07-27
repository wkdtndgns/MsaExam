const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      sorter: (a, b) =>  b.id - a.id,
      sortDirections: ['descend'],
    },
    {
      title: '이름',
      dataIndex: 'name',
    },
    {
      title: '점수',
      dataIndex: 'score',
      sorter: (a, b) => a.score - b.score,
    },
  ];


  export default columns;