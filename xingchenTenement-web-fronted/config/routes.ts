export default [
  {
    path: '/user',
    layout: false,
    routes: [
      { path: '/user/login', component: './User/Login' },
      { path: '/user/register', component: './User/Register' }
    ]
  },
  {
    path: '/admin',
    icon: 'crown',
    name: "管理页",
    access: 'canAdmin',
    routes: [
      { path: '/admin', redirect: '/admin/user' },
      { icon: 'table', path: '/admin/user', component: './Admin/User', name: "用户管理" },
      { icon: 'table', path: '/admin/tenement', component: './Admin/Tenement', name: "租户管理" },
    ],
  },
  { path: '/', icon: 'home', component: './Index', name: "主页" },
  { path: '*', layout: false, component: './404' },
];
