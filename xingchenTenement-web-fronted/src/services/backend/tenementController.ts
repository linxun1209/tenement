// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** addTenement POST /api/tenement/add */
export async function addTenementUsingPost(
  body: API.TenementAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponselong>('/api/tenement/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteTenement POST /api/tenement/delete */
export async function deleteTenementUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseboolean>('/api/tenement/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** editTenement POST /api/tenement/edit */
export async function editTenementUsingPost(
  body: API.TenemenEditRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseboolean>('/api/tenement/edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getTenementVOById GET /api/tenement/get/vo */
export async function getTenementVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getTenementVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseTenementVo>('/api/tenement/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listTenementVOByPage POST /api/tenement/list/page/vo */
export async function listTenementVoByPageUsingPost(
  body: API.TenementQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageTenementVo>('/api/tenement/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyTenementVOByPage POST /api/tenement/my/list/page/vo */
export async function listMyTenementVoByPageUsingPost(
  body: API.TenementQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageTenementVo>('/api/tenement/my/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** updateTenement POST /api/tenement/update */
export async function updateTenementUsingPost(
  body: API.TenementUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseboolean>('/api/tenement/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
