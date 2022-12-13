import React from "react";
import { Divider, Layout, Typography } from "antd";
import { Card, Col, Row } from "antd";
const { Title, Paragraph, Text } = Typography;

class Index extends React.Component {
  render() {
    return (
      <Layout
        style={{
          height: "100%",
          overflowY: "scroll",
          overflowX: "hidden",
        }}
      >
        <Typography>
          <Title>Классификатор</Title>
          <Paragraph>
            Целью данной работы является разработка приложения для классификации
            текста и файлов. <br /> За основу веб-приложения была взята
            микро-сервисная архитектура, с использованием{" "}
            <Text strong>python, java, apache, react</Text>.
          </Paragraph>
          <Paragraph>
            Преимущество такого подхода - независимость пользователя от
            операционной системы. Пользователь сможет пользоваться приложением
            на любом устройстве, где есть веб-браузер.
          </Paragraph>
          <Title level={2}>Разработчики</Title>
          <Paragraph>
            Фронт, классификатор: Орешкин Дмитрий Евгеньевич
          </Paragraph>
          <Paragraph>Весь бэк: Бозоян Оганес Норайрович</Paragraph>

          <Title level={2}>Информация о классификаторе</Title>
          <Paragraph>
            В машинном обучении под классификацией понимают задачу определения
            категории, к которой принадлежит ранее не встречавшийся образец, на
            основании обучающего множества, для элементов которого эти категории
            известны. Это является примером обучения с учителем (supervised
            learning).
          </Paragraph>
          <Paragraph>
            Существует множество подходов к классификации: деревья принятия
            решений, нейронные сети, вероятностные методы и т.д.. В данной
            работе было решено использовать Наивный Байесовский классификатор
            из-за легкости реализации и точности классифицирования текста,
            который предоставляет этот метод.
          </Paragraph>
        </Typography>

        <Divider>О технологиях</Divider>

        <div className="site-card-wrapper">
          <Row gutter={16}>
            <Col span={8}>
              <Card title="Java" bordered={false}>
                В качестве backend части был использован фреймворк Spring
                Framework, основанный на языке Java. Также для хранения
                обработанных данных используется реляционная база данных MySQL.
              </Card>
            </Col>
            <Col span={8}>
              <Card title="Python" bordered={false}>
                Сервер классификации используется для классификации текста. Он
                делится на две части: сам сервер (flask) для общения c
                spring-приложением, и классификатор, построенный на библиотеке
                <Text strong> scikit-learn</Text>.
              </Card>
            </Col>
            <Col span={8}>
              <Card title="React" bordered={false}>
                Frontend часть - это React приложение, использующее restful api,
                которое предоставляет Spring Framework. Построен на классовых
                компонентах и честном слове.
              </Card>
            </Col>
          </Row>
        </div>
      </Layout>
    );
  }
}

export default Index;
