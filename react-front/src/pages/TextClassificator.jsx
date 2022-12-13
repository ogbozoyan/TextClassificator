import { Component } from "react";
import { connect } from "react-redux";
import { Form, Button, Input, Divider, Layout, Empty } from "antd";
import { Descriptions } from "antd";

class TextClassificator extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isFetching: false,
      token: "",
      text: "",
      id: "",
      category: "",
    };

    this.layout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 12 },
    };

    this.buttonItemLayout = {
      wrapperCol: { span: 12, offset: 6 },
    };

    this.sendText = this.sendText.bind(this);
  }

  sendText = () => {
    let data = {
      payload: this.state.text,
    };
    data = JSON.stringify(data);
    console.log(data);

    fetch(`http://127.0.0.1:8000/${this.state.token}/upload/text/`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: data,
    })
      .then((response) => {
        return response.json();
      })
      .then((response) => {
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
    this.setState({ ...this.state, isFetching: true });
    this.sendText();
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
          <Form.Item name="text" label="Текст" rules={[{ required: true }]}>
            <Input.TextArea
              autoSize={{ minRows: 10, maxRows: 10 }}
              onChange={(e) =>
                this.setState({ ...this.state, text: e.target.value })
              }
            />
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

        {this.state.id == 0 ? (
          <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
        ) : (
          <Descriptions title="Результат классификации" column={1}>
            <Descriptions.Item label="id">{this.state.id}</Descriptions.Item>
            <Descriptions.Item label="Категория">
              {this.state.category}
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

export default connect(mapStateToProps, mapDispatchToProps)(TextClassificator);
