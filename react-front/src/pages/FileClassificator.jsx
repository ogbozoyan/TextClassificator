import { Component } from "react";
import { connect } from "react-redux";
import { Form, Button, Input, Upload, Divider, Layout, Empty } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import axios from "axios";

import { Descriptions } from "antd";

class FileClassificator extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isFetching: false,
      token: "",
      file: {},
      id: 0,
      category: "",
    };

    this.layout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 12 },
    };

    this.buttonItemLayout = {
      wrapperCol: { span: 12, offset: 6 },
    };

    this.sendFile = this.sendFile.bind(this);
  }

  beforeUpload = (file) => {
    this.setState({ ...this.state, file: file });
    return false;
  };

  onRemove = (file) => {
    this.setState({ ...this.state, id: 0, file: {} });
  };

  sendFile = () => {
    let data = new FormData();
    data.append("file", this.state.file);

    fetch(`http://172.20.10.7:8080/${this.state.token}/upload/file/`, {
      method: "POST",
      body: data,
    })
      .then((response) => {
        // console.log(response);
        return response.json();
      })
      .then((response) => {
        console.log(response);
        this.setState({
          ...this.state,
          isFetching: false,
          id: response.id,
          category: response.result,
        });
      })
      .catch((err) => {
        this.setState({
          ...this.state,
          isFetching: false,
          id: -1,
          category: `${err}`,
        });
      });
  };

  onFinish = (e) => {
    // e.preventDefault;
    this.setState({ ...this.state, isFetching: true });
    this.sendFile();
  };

  render() {
    return (
      <Layout
        style={{
          height: "100%",
          overflowY: "scroll",
          overflowX: "hidden",
        }}
      >
        <Form {...this.layout} onFinish={(e) => this.onFinish(e)}>
          <Form.Item name="file" label="Файл" rules={[{ required: true }]}>
            <Upload.Dragger
              //   action="http://127.0.0.1/token/upload/"
              beforeUpload={this.beforeUpload}
              listType="picture"
              onRemove={this.onRemove}
              customRequest={() => false}
            >
              <Button icon={<UploadOutlined />}>Upload</Button>
            </Upload.Dragger>
          </Form.Item>

          <Form.Item name="token" label="Токен" rules={[{ required: true }]}>
            <Input
              onChange={(e) =>
                this.setState({ ...this.state, token: e.target.value })
              }
            />
          </Form.Item>

          <Form.Item {...this.buttonItemLayout}>
            <Button
              type="primary"
              htmlType="submit"
              loading={this.state.isFetching}
              block={true}
            >
              Отправить
            </Button>
          </Form.Item>
        </Form>

        <Divider>Результат классификации</Divider>
        {this.state.id === 0 ? (
          <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
        ) : (
          <Descriptions title="" column={1}>
            <Descriptions.Item label="id">{this.state.id}</Descriptions.Item>
            <Descriptions.Item label="Категория">
              {this.state.category}
            </Descriptions.Item>
          </Descriptions>
        )}

        <Divider>Информация о файле</Divider>

        {this.state.file.name == undefined ? (
          <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
        ) : (
          <Descriptions title="" column={1}>
            <Descriptions.Item label="Имя файла">
              {this.state.file?.name}
            </Descriptions.Item>
            <Descriptions.Item label="Размер файла">
              {this.state.file?.size}
            </Descriptions.Item>
            <Descriptions.Item label="Тип файла">
              {this.state.file?.type}
            </Descriptions.Item>
          </Descriptions>
        )}
      </Layout>
    );
  }
}

let mapStateToProps = () => {
  return {};
};

let mapDispatchToProps = (dispatch) => {
  return {
    setNewText: (text) => dispatch({ type: "SET_TEXT", payload: text }),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(FileClassificator);
