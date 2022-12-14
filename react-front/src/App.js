import Index from "./pages/Index";
import TextClassificator from "./pages/TextClassificator";
import FileClassificator from "./pages/FileClassificator";

import React from "react";
import { Routes, Route, Link, Navigate } from "react-router-dom";
import {
  UserOutlined,
  FileDoneOutlined,
  InfoOutlined,
  GithubOutlined,
} from "@ant-design/icons";
import { Layout, Menu } from "antd";
import { connect } from "react-redux";

const { Header, Sider, Content } = Layout;

class App extends React.Component {
  constructor(props) {
    super(props);
    this.colorBgContainer = "#d9d9d9";
    this.state = {
      collapsed: false,
    };
  }

  componentDidMount() {}

  componentDidUpdate() {
    alert("update!");
  }

  render() {
    return (
      <Layout
        hasSider={true}
        style={{
          height: "100%",
        }}
      >
        <Sider
          breakpoint="lg"
          collapsedWidth="0"
          onBreakpoint={(broken) => {}}
          onCollapse={(collapsed, type) => {}}
        >
          <div className="logo" />
          <Menu
            theme="dark"
            mode="inline"
            defaultSelectedKeys={["1"]}
            style={{ alignSelf: "center" }}
          >
            <Menu.Item key="1" icon={<UserOutlined />}>
              <Link to="/">Главная</Link>
            </Menu.Item>
            <Menu.Item key="2" icon={<InfoOutlined />}>
              <Link to="/text">Текст</Link>
            </Menu.Item>
            <Menu.Item key="3" icon={<FileDoneOutlined />}>
              <Link to="/file">Файл</Link>
            </Menu.Item>
          </Menu>
        </Sider>
        <Layout
          className="site-layout"
          style={{
            background: this.colorBgContainer,
          }}
        >
          <Header
            style={{
              paddingLeft: 24,
              paddingRight: 24,
              display: "flex",
              flexDirection: "row",
              justifyContent: "space-between",
              // textAlign:"center",
              background: "#f5f5f5",
            }}
          >
            <span>Classificator</span>
            <GithubOutlined style={{ alignSelf: "center" }} />
          </Header>
          <Content
            style={{
              margin: "24px 16px",
              padding: 24,
              minHeight: "280px",
              background: "#f5f5f5",
            }}
          >
            <Routes>
              <Route path="/" element={<Index />} />
              <Route path="/text" element={<TextClassificator />} />
              <Route path="/file" element={<FileClassificator />} />
              <Route path="*" element={<Navigate to="/" />} />
            </Routes>
          </Content>
        </Layout>
      </Layout>
    );
  }
}

let mapStateToProps = (state) => {
  return {
    text: state.text,
  };
};

let mapDispatchToProps = (dispatch) => {
  return {};
};

export default connect(mapStateToProps, mapDispatchToProps)(App);
