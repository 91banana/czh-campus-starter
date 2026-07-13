<template>
  <div>
    <n-grid :cols="4" :x-gap="16" :y-gap="16">
      <n-gi>
        <n-card>
          <n-statistic label="注册用户" :value="overview.userCount">
            <template #prefix><span style="font-size:20px">👤</span></template>
          </n-statistic>
        </n-card>
      </n-gi>
      <n-gi>
        <n-card>
          <n-statistic label="商品总数" :value="overview.productCount">
            <template #prefix><span style="font-size:20px">📦</span></template>
          </n-statistic>
        </n-card>
      </n-gi>
      <n-gi>
        <n-card>
          <n-statistic label="公告数量" :value="overview.announcementCount">
            <template #prefix><span style="font-size:20px">📢</span></template>
          </n-statistic>
        </n-card>
      </n-gi>
      <n-gi>
        <n-card>
          <n-statistic label="失物招领" :value="overview.lostFoundCount">
            <template #prefix><span style="font-size:20px">🔍</span></template>
          </n-statistic>
        </n-card>
      </n-gi>
    </n-grid>

    <n-grid :cols="2" :x-gap="16" :y-gap="16" style="margin-top: 16px">
      <n-gi>
        <n-card title="商品分类分布">
          <div ref="categoryChartRef" style="height: 300px"></div>
        </n-card>
      </n-gi>
      <n-gi>
        <n-card title="商品状态分布">
          <div ref="statusChartRef" style="height: 300px"></div>
        </n-card>
      </n-gi>
    </n-grid>

    <n-grid :cols="1" :x-gap="16" :y-gap="16" style="margin-top: 16px">
      <n-gi>
        <n-card title="用户学院分布">
          <div ref="collegeChartRef" style="height: 300px"></div>
        </n-card>
      </n-gi>
    </n-grid>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import { NGrid, NGi, NCard, NStatistic } from 'naive-ui'
import * as echarts from 'echarts'
import { useUserStore } from '../../stores/user'

const userStore = useUserStore()

const overview = ref<Record<string, number>>({
  userCount: 0,
  productCount: 0,
  announcementCount: 0,
  lostFoundCount: 0,
})

const categoryChartRef = ref<HTMLElement | null>(null)
const statusChartRef = ref<HTMLElement | null>(null)
const collegeChartRef = ref<HTMLElement | null>(null)

async function fetchStats(path: string) {
  const res = await fetch(`/api/admin/stats${path}`, {
    headers: { Authorization: `Bearer ${userStore.token}` },
  })
  const json = await res.json()
  return json.data
}

onMounted(async () => {
  const ov = await fetchStats('/overview')
  if (ov) overview.value = ov

  await nextTick()

  const catData = await fetchStats('/products-by-category')
  if (categoryChartRef.value && catData) {
    const chart = echarts.init(categoryChartRef.value)
    chart.setOption({
      tooltip: { trigger: 'item' },
      series: [{
        type: 'pie',
        radius: ['40%', '70%'],
        data: catData,
        emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0,0,0,0.5)' } },
      }],
    })
  }

  const statusData = await fetchStats('/products-by-status')
  if (statusChartRef.value && statusData) {
    const chart = echarts.init(statusChartRef.value)
    chart.setOption({
      tooltip: { trigger: 'item' },
      series: [{
        type: 'pie',
        radius: '60%',
        data: statusData,
        itemStyle: {
          color: (params: any) => {
            const colors: Record<string, string> = { '上架': '#50C878', '下架': '#FFB347', '已交易': '#4A90D9' }
            return colors[params.name] || '#999'
          },
        },
      }],
    })
  }

  const collegeData = await fetchStats('/users-by-college')
  if (collegeChartRef.value && collegeData) {
    const chart = echarts.init(collegeChartRef.value)
    chart.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: collegeData.map((d: any) => d.name || '未填写') },
      yAxis: { type: 'value' },
      series: [{
        type: 'bar',
        data: collegeData.map((d: any) => d.value),
        itemStyle: { color: '#4A90D9' },
      }],
    })
  }
})
</script>
