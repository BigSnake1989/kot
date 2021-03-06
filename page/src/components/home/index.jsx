import React,{ Component } from 'react';
import styles from '../../layouts/MainLayout/MainLayout.less';

class Home extends Component {
    render(){
        return (
            <div className={styles.t}>
                <table cellSpacing="0" cellPadding="0">
                    <tbody>
                        <tr className={styles.trdesc}>
                            <td style={{ width: 80 }}>分类</td>
                            <td>文章</td>
                            <td style={{ width: 100 }}>作者</td>
                            <td style={{ width: 80 }}>回复</td>
                            <td style={{ width: 200 }}>最后发表</td>
                        </tr>
                        <tr className={styles.trcontent}>
                            <td className={styles.tdcenter}>test</td>
                            <td className={styles.tdleft}>hahahahahahha.........................</td>
                            <td className={styles.tdcenter}>zz</td>
                            <td className={styles.tdcenter}>90</td>
                            <td className={styles.tdcenter}>mm</td>
                        </tr>
                    </tbody>
                </table>
                <Pagination />
            </div>
        )
    }
}

export default Home;

class Pagination extends Component {
    render(){
        return (
            <div className={styles.page}>
                <table cellSpacing="0" cellPadding="0">
                    <tbody>
                        <tr>
                            <td>{'<'}</td>
                            <td>上一页</td>
                            <td>下一页</td>
                            <td>{'>'}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        )
    }
}
