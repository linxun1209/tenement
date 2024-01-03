import CreateModal from '@/pages/Admin/Tenement/components/CreateModal';
import UpdateModal from '@/pages/Admin/Tenement/components/UpdateModal';
import {PlusOutlined} from '@ant-design/icons';
import {ActionType, PageContainer, ProColumns} from '@ant-design/pro-components';
import {ProTable} from '@ant-design/pro-components';
import '@umijs/max';
import {Button, message, Space, Typography} from 'antd';
import React, {useRef, useState} from 'react';
import {deleteTenementUsingPost, listTenementVoByPageUsingPost} from "@/services/backend/tenementController";

/**
 * 用户管理页面
 *
 * @constructor
 */
const TenementAdminPage: React.FC = () => {
  // 是否显示新建窗口
  const [createModalVisible, setCreateModalVisible] = useState<boolean>(false);
  // 是否显示更新窗口
  const [updateModalVisible, setUpdateModalVisible] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  // 当前用户点击的数据
  const [currentRow, setCurrentRow] = useState<API.Tenement>();

  /**
   * 删除节点
   *
   * @param row
   */
  const handleDelete = async (row: API.Tenement) => {
    const hide = message.loading('正在删除');
    if (!row) return true;
    try {
      await deleteTenementUsingPost({
        id: row.id as any,
      });
      hide();
      message.success('删除成功');
      actionRef?.current?.reload();
      return true;
    } catch (error: any) {
      hide();
      message.error('删除失败，' + error.message);
      return false;
    }
  };

  /**
   * 表格列配置
   */
  const columns: ProColumns<API.Tenement>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'text',
      hideInForm: true,
    },
    {
      title: '姓名',
      dataIndex: 'name',
      valueType: 'text',
    },
    {
      title: '性别',
      dataIndex: 'gender',
      valueEnum: {
        男: {
          text: '男',
        },
        女: {
          text: '女',
        },
        }
    },
    {
      title: '年龄',
      dataIndex: 'age',
      valueType: 'text',
    },
    {
      title: '身份证',
      dataIndex: 'icn',
      valueType: 'text',
    },
    {
      title: '户型',
      dataIndex: 'houseHoldType',
      valueType: 'text'
    },
    {
      title: '电话号',
      dataIndex: 'tel',
      valueType: 'text'
    },
    {
      title: '门牌号',
      dataIndex: 'houseNumber',
      valueType: 'text'
    },
    {
      title: '租房金额',
      dataIndex: 'rent',
      valueType: 'text'
    },
    {
      title: '租入时间',
      sorter: true,
      dataIndex: 'rentTime',
      valueType: 'dateTime',
    },
    {
      title: '合同到期时间',
      sorter: true,
      dataIndex: 'expirationDate',
      valueType: 'dateTime',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <Space size="middle">
          <Typography.Link
            onClick={() => {
              setCurrentRow(record);
              setUpdateModalVisible(true);
            }}
          >
            修改
          </Typography.Link>
          <Typography.Link type="danger" onClick={() => handleDelete(record)}>
            删除
          </Typography.Link>
        </Space>
      ),
    },
  ];
  return (
    <PageContainer>
      <ProTable<API.Tenement>
        headerTitle={'查询表格'}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              setCreateModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={async (params, sort, filter) => {
          const sortField = Object.keys(sort)?.[0];
          const sortOrder = sort?.[sortField] ?? undefined;

          const { data, code } = await listTenementVoByPageUsingPost({
            ...params,
            sortField,
            sortOrder,
            ...filter,
          } as API.UserQueryRequest);

          return {
            success: code === 0,
            data: data?.records || [],
            total: Number(data?.total) || 0,
          };
        }}
        columns={columns}
      />
      <CreateModal
        visible={createModalVisible}
        columns={columns}
        onSubmit={() => {
          setCreateModalVisible(false);
          actionRef.current?.reload();
        }}
        onCancel={() => {
          setCreateModalVisible(false);
        }}
      />
      <UpdateModal
        visible={updateModalVisible}
        columns={columns}
        oldData={currentRow}
        onSubmit={() => {
          setUpdateModalVisible(false);
          setCurrentRow(undefined);
          actionRef.current?.reload();
        }}
        onCancel={() => {
          setUpdateModalVisible(false);
        }}
      />
    </PageContainer>
  );
};
export default TenementAdminPage;
