import { UserOutlined } from '@ant-design/icons';
import { PageContainer, ProFormText, QueryFilter } from '@ant-design/pro-components';

import { Avatar, Card, Flex, Image, Input, List, message,Typography } from 'antd';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import {listTenementVoByPageUsingPost} from "@/services/backend/tenementController";

/**
 * 默认分页参数
 */
const DEFAULT_PAGE_PARAMS: PageRequest = {
  current: 1,
  pageSize: 4,
  sortField: 'rentTime',
  sortOrder: 'descend',
};

/**
 * 主页
 * @constructor
 */
const IndexPage: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [dataList, setDataList] = useState<API.TenementVo[]>([]);
  const [total, setTotal] = useState<number>(0);
  // 搜索条件
    const [searchParams, setSearchParams] = useState<API.TenementQueryRequest>({
        ...DEFAULT_PAGE_PARAMS,
    });

  /**
   * 搜索
   */
  const doSearch = async () => {
    setLoading(true);
    try {
      const res = await listTenementVoByPageUsingPost(searchParams);
      setDataList(res.data?.records ?? []);
      setTotal(Number(res.data?.total) ?? 0);
    } catch (error: any) {
      message.error('获取数据失败，' + error.message);
    }
    setLoading(false);
  };

  useEffect(() => {
    doSearch();
  }, [searchParams]);


  return (
    <PageContainer title={<></>}>
      <Flex justify="center">
        <Input.Search
          style={{
            width: '40vw',
            minWidth: 320,
          }}
          placeholder="租户信息管理"
          allowClear
          enterButton="搜索"
          size="large"
          onChange={(e) => {
            searchParams.searchText = e.target.value;
          }}
          onSearch={(value: string) => {
            setSearchParams({
              ...searchParams,
              ...DEFAULT_PAGE_PARAMS,
              searchText: value,
            });
          }}
        />
      </Flex>
      <div style={{ marginBottom: 16 }} />


      <QueryFilter
        span={12}
        labelWidth="auto"
        labelAlign="left"
        defaultCollapsed={false}
        style={{ padding: '16px 0' }}
        onFinish={async (values: API.TenementVo) => {
          setSearchParams({
            ...DEFAULT_PAGE_PARAMS,
            // @ts-ignore
            ...values,
            searchText: searchParams.searchText,
          });
        }}
      ><ProFormText label="租户名" name="name" />
        <ProFormText label="户型" name="houseHoldType" />
      </QueryFilter>

      <div style={{ marginBottom: 24 }} />

      <List<API.TenementVo>
        rowKey="id"
        loading={loading}
        grid={{
          gutter: 16,
          xs: 1,
          sm: 2,
          md: 3,
          lg: 3,
          xl: 4,
          xxl: 4,
        }}
        dataSource={dataList}
        pagination={{
          current: searchParams.current,
          pageSize: searchParams.pageSize,
          total,
          onChange(current: number, pageSize: number) {
            setSearchParams({
              ...searchParams,
              current,
              pageSize,
            });
          },
        }}
        renderItem={(data) => (
          <List.Item>
            <Card hoverable cover={<Image alt={data.name} src={data.user?.userAvatar ?? <UserOutlined />} />}>
              <Card.Meta
                title={<a>{data.name}</a>}
                description={
                  <Typography.Paragraph ellipsis={{ rows: 2 }} style={{ height: 44 }}>
                    {data.houseHoldType}
                  </Typography.Paragraph>
                }
              />
              <Flex justify="space-between" align="center">
                <Typography.Text type="secondary" style={{ fontSize: 12 }}>
                  {moment(data.rentTime).fromNow()}
                </Typography.Text>
                <div>
                  <Avatar src={data.user?.userAvatar ?? <UserOutlined />} />
                </div>
              </Flex>
            </Card>
          </List.Item>
        )}
      />
    </PageContainer>
  );
};

export default IndexPage;
