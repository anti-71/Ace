def binary_to_decimal():
    # 获取用户输入的二进制小数
    binary_str = input("请输入一个二进制小数（例如：101.101）: ")

    # 检查输入是否有效
    if not all(c in '01.' for c in binary_str) or binary_str.count('.') > 1:
        print("输入无效，请输入有效的二进制小数。")
        return

    # 分离整数部分和小数部分
    if '.' in binary_str:
        integer_part_str, fractional_part_str = binary_str.split('.')
    else:
        integer_part_str = binary_str
        fractional_part_str = ''

    # 转换整数部分为十进制
    integer_decimal = 0
    for i, digit in enumerate(reversed(integer_part_str)):
        integer_decimal += int(digit) * (2 ** i)

    # 转换小数部分为十进制
    fractional_decimal = 0
    for i, digit in enumerate(fractional_part_str):
        fractional_decimal += int(digit) * (2 ** -(i + 1))

    # 计算最终结果
    result = integer_decimal + fractional_decimal

    # 输出结果
    print(f"二进制小数 {binary_str} 的十进制表示为: {result}")

# 调用函数
binary_to_decimal()