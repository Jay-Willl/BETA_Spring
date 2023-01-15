<template>
  <div class="title">充值</div>
  <el-row gutter=50>
    <el-col span=16>
    <div class="tip">
      请选择充值金额：
    </div>
    <div class="payList">
      <el-radio-group v-model="checkbox1" size="default" @change="changeCheckbox1">
        <el-radio-button label=10>{{'¥10'}}</el-radio-button>
        <el-radio-button label=50>{{'¥50'}}</el-radio-button>
        <el-radio-button label=100>{{'¥100'}}</el-radio-button>
        <el-radio-button label=500>{{'¥500'}}</el-radio-button>
        <el-radio-button label=1000>{{'¥1000'}}</el-radio-button>
        <el-radio-button label=-1>{{'自定义'}}</el-radio-button>
      </el-radio-group>
    </div>
      <br>
      <el-input
          v-model="amountInput"
          placeholder="请输入充值金额"
          class="input"
          :disabled="amountUsed"
          size="default">
      </el-input>
    <div>
      <el-button
          class="payButton"
          round
          size="large"
          @click="toPay()"
      >
        确认支付
      </el-button>
    </div>
    </el-col>
    <el-col span=8>
      <el-card style="width: 300px">
        <el-descriptions class="margin-top"  :column="1" border size="default">
          <template #title>
            当前余额
            <el-button
                id="refreshButton"
                circle
                @click="toRefresh()"
                size="large"
            >
              <el-icon><Refresh /></el-icon>
            </el-button>
          </template>
          <el-descriptions-item>
            <template #label>
              <el-icon><User /></el-icon>
              用户id
            </template>
            <div class="text item">{{this.user.userId}}</div>
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <el-icon><Money /></el-icon>
              用户余额
            </template>
            <div class="text item">{{'¥' + this.user.gold }}</div>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>

    </el-col>
  </el-row>
</template>

<script>
import aliPayApi from '../api/aliPay'
import userApi from '../api/user'
import cookie from "js-cookie";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "payment",
  data(){
    return{
      user:{
        userId: -1,
        gold: 0,
      },
      checkbox1:10,
      amountInput:null,
      amountUsed:true,
    }
  },
  methods:{
    goBack(){
      window.location.href = "/#/";
    },
    changeCheckbox1(){
      this.amountUsed = this.checkbox1 > 0
      this.amountInput = null
    },
    toPay() {
      if (this.checkbox1 < 0){
        this.checkbox1 = this.amountInput;
      }
      aliPayApi.tradePagePay(this.checkbox1*100, this.user.userId).then((response) => {
        //将支付宝返回的表单字符串写在浏览器中，表单会自动触发submit提交
        console.log(response.data)
        var newWin = window.open('_blank')
        newWin.document.body.innerHTML = response.data;
        newWin.document.forms[0].submit();
        // document.write(response.data)
      })
    },
    toRefresh() {
      let userId = cookie.get('user_id')
      aliPayApi.refresh().then(
          () => userApi.getUserInfo(userId).then(
              (response) => {
            this.user.userId = userId
            this.user.gold = response.data.user.user_gold/100
            cookie.set('user_gold',response.data.user.user_gold)
              }))
    },
  },
  created() {
    let userId = cookie.get('user_id')
    userApi.getUserInfo(userId).then((response) => {
      this.user.userId = userId
      this.user.gold = response.data.user.user_gold/100
    })
  }
}
</script>

<style scoped>

.page-header{
  margin-bottom: 20px;
}

.tip{
  margin-left: 20px;
}

.payList{
  margin-top: 25px;
  margin-left: 25px;
  margin-bottom: 25px;
  display: inline-block;
}

.text {
  font-size: 14px;
  text-align: left;
}

.item {
  margin-bottom: 10px;
  margin-top: 10px;
}

.input {
  width: 128px;
  margin-left: 310px;
}

.payButton{
  margin-left: 25px;
}

#refreshButton{
  margin-left: 75px;
}

.title{
  margin-top: 25px;
  margin-left: 20px;
  margin-bottom: 25px;
  font-size: 20px;
}

</style>