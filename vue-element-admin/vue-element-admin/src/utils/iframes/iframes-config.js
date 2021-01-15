/**
 * 是否启用全局iframes缓存组件
 */
export const IFRAMES_ENABLE = true

/**
 * 配置模块信息
 * 注：模块ID请勿重复
 */
// 百度
const URL_ORIGIN = 'http://localhost:28080/cockpit/manage.html' // process.env.VUE_APP_WS_URL
const URL_PAGE = {
  'BAI_DU': {
    name: '百度一下',
    src: URL_ORIGIN
  },
  'DIAGRAMS': {
    name: 'diagrams',
    src: 'http://localhost:28080/cockpit/app/tjfx/index.html?widgetId=layout_1606707051240'
  }
}

const IFRAMES_PAGE = { ...URL_PAGE }
export function PAGE_LIST() {
  return IFRAMES_PAGE
}
