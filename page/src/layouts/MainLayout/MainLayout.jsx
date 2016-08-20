import React, { Component, PropTypes } from 'react';
import { Router, Route, IndexRoute, Link } from 'react-router';
import styles from './MainLayout.less';

const MainLayout = ({ children }) => {
  return (
    <div className={styles.normal}>
      <div className={styles.head}>
        <div className={styles.title}><h1>Kotin China</h1></div>
        <Navi />
      </div>
      <div className={styles.content}>
        <div className={styles.main}>
          {children}
        </div>
      </div>
      <div className={styles.foot}>
        Built with react, react-router, ant-tool, css-modules, antd...
      </div>
    </div>
  );
};

MainLayout.propTypes = {
  children: PropTypes.element.isRequired,
};

export default MainLayout;

class Navi extends Component{
    render(){
        return (
            <div className={styles.navi}>
                Home
                | Discuss | Login | Register
            </div>
        )
    }
}
