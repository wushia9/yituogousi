# RestfulApi
| 注解             | 用途                  | api         |
|----------------|---------------------|-------------|
| @GetMapping    | 获得所有账单              | /bills      |
| @GetMapping    | 获得一个账单              | /bills/{id} |
| @PostMapping   | 新增一个账单              | /bills/{id} |
| @PutMapping    | 修改一个账单（前端传整个账单实体类）  | /bills/{id} |
| @PatchMapping  | 修改一个账单（前端传需要修改的具体属性 | /bills/{id} |
| @DeleteMapping | 删除一个账单              | /bills/{id} |

## 前提
Restful风格的api地址一定要使用单词的复数形式
Get 得到一个账单 /bill 错误
Get 得到一个账单 /bills/{id} 正确

## 前端请求
api.request(url, method, (data))
api.request('/bills/{id}', 'GET', (data))
api.request('/bills/{id}', 'POST', (data))

/users/{id}/bills/{id} Put  修改某个用户的某个账单