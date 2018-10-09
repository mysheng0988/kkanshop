package com.mysheng.office.kkanshop;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mysheng.office.kkanshop.adapter.ShopcartExpandableListViewAdapter;
import com.mysheng.office.kkanshop.entity.GroupInfo;
import com.mysheng.office.kkanshop.entity.ProductInfo;
import com.mysheng.office.kkanshop.view.SuperExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartFragment extends Fragment implements ShopcartExpandableListViewAdapter.CheckInterface, View.OnClickListener, ShopcartExpandableListViewAdapter.ModifyCountInterface {
	private SuperExpandableListView exListView;
	private CheckBox cb_check_all;
	private TextView tv_total_price;
	private TextView tv_delete;
	private TextView tv_go_to_pay;
	private Context context;

	private double totalPrice = 0.00;// 购买的商品总价
	private int totalCount = 0;// 购买的商品总数量

	private ShopcartExpandableListViewAdapter selva;
	private List<GroupInfo> groups = new ArrayList<GroupInfo>();// 组元素数据列表
	private Map<String, List<ProductInfo>> children = new HashMap<>();// 子元素数据列表
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.tab04, container, false);
		initView(view);
		initEvents();
		return view ;
	}
	private void initView(View view) {
		context = getActivity();
		virtualData();
		exListView = view.findViewById(R.id.exListView);
		cb_check_all = view.findViewById(R.id.all_chekbox);
		tv_total_price = view.findViewById(R.id.tv_total_price);
		tv_delete = view.findViewById(R.id.tv_delete);
		tv_go_to_pay =view. findViewById(R.id.tv_go_to_pay);
	}

	/**
	 * 模拟数据<br>
	 * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
	 * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
	 */
	private void virtualData() {

		for (int i = 0; i < 7; i++) {
			groups.add(new GroupInfo(i + "", "第八号当铺" + (i + 1) + "号店"));
			List<ProductInfo> products = new ArrayList<>();
			for (int j = 0; j <= i; j++) {
				products.add(new ProductInfo(j + "", "商品", "", groups.get(i).getName() + "的第" + (j + 1) + "个商品", 120.00 + i * j, 1+j));
			}
			children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
		}
	}

	private void initEvents() {
		selva = new ShopcartExpandableListViewAdapter(groups, children, getActivity());
		selva.setCheckInterface(this);// 关键步骤1,设置复选框接口
		selva.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
		exListView.setAdapter(selva);

		for (int i = 0; i < selva.getGroupCount(); i++) {
			exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
		}

		cb_check_all.setOnClickListener(this);
		tv_delete.setOnClickListener(this);
		tv_go_to_pay.setOnClickListener(this);
	}
	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		AlertDialog alert;
		switch (v.getId()) {
			case R.id.all_chekbox:
				doCheckAll();
				break;
			case R.id.tv_go_to_pay:
				if (totalCount == 0) {
					Toast.makeText(context, "请选择要支付的商品", Toast.LENGTH_LONG).show();
					return;
				}
//				alert = new AlertDialog.Builder(context).create();
//				alert.setTitle("操作提示");
//				alert.setMessage("总计:\n" + totalCount + "种商品\n" + totalPrice + "元");
//				alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						return;
//					}
//				});
//				alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener(){
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						return;
//					}
//				});
//				alert.show();
				Intent intent=new Intent(getActivity(),InfoOrderActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_delete:
				if (totalCount == 0) {
					Toast.makeText(context, "请选择要移除的商品", Toast.LENGTH_LONG).show();
					return;
				}
				alert = new AlertDialog.Builder(context).create();
				alert.setTitle("操作提示");
				alert.setMessage("您确定要将这些商品从购物车中移除吗？");
				alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
				alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						doDelete();
					}
				});
				alert.show();
				break;
		}
	}

	/**
	 * 组选框状态改变触发的事件
	 *
	 * @param groupPosition 组元素位置
	 * @param isChecked     组元素选中与否
	 */
	@Override
	public void checkGroup(int groupPosition, boolean isChecked) {
		GroupInfo group = groups.get(groupPosition);
		List<ProductInfo> childs = children.get(group.getId());
		for (int i = 0; i < childs.size(); i++) {
			childs.get(i).setChoosed(isChecked);
		}
		if (isAllCheck())
			cb_check_all.setChecked(true);
		else
			cb_check_all.setChecked(false);
		selva.notifyDataSetChanged();
		calculate();
	}

	/**
	 * 子选框状态改变时触发的事件
	 *
	 * @param groupPosition 组元素位置
	 * @param childPosition 子元素位置
	 * @param isChecked     子元素选中与否
	 */
	@Override
	public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
		boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
		GroupInfo group = groups.get(groupPosition);
		List<ProductInfo> childs = children.get(group.getId());
		for (int i = 0; i < childs.size(); i++) {
			if (childs.get(i).isChoosed() != isChecked) {
				allChildSameState = false;
				break;
			}
		}
		if (allChildSameState) {
			group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
		} else {
			group.setChoosed(false);// 否则，组元素一律设置为未选中状态
		}

		if (isAllCheck())
			cb_check_all.setChecked(true);
		else
			cb_check_all.setChecked(false);
		selva.notifyDataSetChanged();
		calculate();
	}

	/**
	 * 增加操作
	 *
	 * @param groupPosition 组元素位置
	 * @param childPosition 子元素位置
	 * @param showCountView 用于展示变化后数量的View
	 * @param isChecked     子元素选中与否
	 */
	@Override
	public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
		ProductInfo product = (ProductInfo) selva.getChild(groupPosition, childPosition);
		int currentCount = product.getCount();
		currentCount++;
		product.setCount(currentCount);
		((TextView) showCountView).setText(currentCount + "");

		selva.notifyDataSetChanged();
		calculate();
	}

	/**
	 * 删减操作
	 *
	 * @param groupPosition 组元素位置
	 * @param childPosition 子元素位置
	 * @param showCountView 用于展示变化后数量的View
	 * @param isChecked     子元素选中与否
	 */
	@Override
	public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
		ProductInfo product = (ProductInfo) selva.getChild(groupPosition, childPosition);
		int currentCount = product.getCount();
		if (currentCount == 1)
			return;
		currentCount--;

		product.setCount(currentCount);
		((TextView) showCountView).setText(currentCount + "");

		selva.notifyDataSetChanged();
		calculate();
	}
	/**
	 * 删除操作<br>
	 * 1.不要边遍历边删除，容易出现数组越界的情况<br>
	 * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
	 */
	public void doDelete() {
		List<GroupInfo> toBeDeleteGroups = new ArrayList<GroupInfo>();// 待删除的组元素列表
		for (int i = 0; i < groups.size(); i++) {
			GroupInfo group = groups.get(i);
			if (group.isChoosed()) {

				toBeDeleteGroups.add(group);
			}
			List<ProductInfo> toBeDeleteProducts = new ArrayList<ProductInfo>();// 待删除的子元素列表
			List<ProductInfo> childs = children.get(group.getId());
			for (int j = 0; j < childs.size(); j++) {
				if (childs.get(j).isChoosed()) {
					toBeDeleteProducts.add(childs.get(j));
				}
			}
			childs.removeAll(toBeDeleteProducts);

		}

		groups.removeAll(toBeDeleteGroups);

		selva.notifyDataSetChanged();
		calculate();
	}

	/** 全选与反选 */
	private void doCheckAll() {
		for (int i = 0; i < groups.size(); i++) {
			groups.get(i).setChoosed(cb_check_all.isChecked());
			GroupInfo group = groups.get(i);
			List<ProductInfo> childs = children.get(group.getId());
			for (int j = 0; j < childs.size(); j++) {
				childs.get(j).setChoosed(cb_check_all.isChecked());
			}
		}
		selva.notifyDataSetChanged();
		calculate();
	}

	/**
	 * 统计操作<br>
	 * 1.先清空全局计数器<br>
	 * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
	 * 3.给底部的textView进行数据填充
	 */
	private void calculate() {
		totalCount = 0;
		totalPrice = 0.00;
		for (int i = 0; i < groups.size(); i++) {
			GroupInfo group = groups.get(i);
			List<ProductInfo> childs = children.get(group.getId());
			for (int j = 0; j < childs.size(); j++) {
				ProductInfo product = childs.get(j);
				if (product.isChoosed()) {
					totalCount++;
					totalPrice += product.getPrice() * product.getCount();
				}
			}
		}
		tv_total_price.setText("￥" + totalPrice);
		tv_go_to_pay.setText("去支付(" + totalCount + ")");
	}

	private boolean isAllCheck() {
		for (GroupInfo group : groups) {
			if (!group.isChoosed())
				return false;
		}
		return true;
	}
}
