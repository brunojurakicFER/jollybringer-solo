import React from 'react';
import Header from "./Header.jsx";

const Dashboard = ({status}) => {
  return (
    <div className={'bg-black'}>
      <Header status={status}/>
      <div className={'flex justify-between'}>
        <div className={'w-1/2'}>
          {/*<Activities/>*/}
        </div>
        <div className={'w-1/2'}>
          {/*<Chat/>*/}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;