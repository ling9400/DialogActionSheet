package com.qk.actionsheet;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DialogActionSheet {
	private Context context;
	private Dialog dialog;
	private LinearLayout llTitle;//头部标题布局
	private TextView txt_title;//标题
	private TextView txt_cancel;//取消按钮
	private ListView sLayout_content;//listView
	private boolean showTitle = false;
	private List<SheetItem> sheetItemList;
	private OnDialogDismissListener dismissListener;
	private Display display;
    private float itemTextSize;//item的字体大小

	public DialogActionSheet(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public DialogActionSheet builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_actionsheet, null);

		// 设置Dialog最小宽度为屏幕宽度
		view.setMinimumWidth(display.getWidth());

		// 获取自定义Dialog布局中的控件
		sLayout_content = (ListView) view.findViewById(R.id.izd_lvActionSheet);
		txt_title = (TextView) view.findViewById(R.id.izd_txt_title);
		llTitle = (LinearLayout) view.findViewById(R.id.izd_llActionTitle);
		txt_cancel = (TextView) view.findViewById(R.id.izd_txt_cancel);
		txt_cancel.setTextSize(16);
		txt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if(dismissListener != null){
					dismissListener.dismiss();
				}
			}
		});

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.izd_ActionSheetDialogStyle);
		dialog.setContentView(view);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				if(dismissListener != null){
					dismissListener.dismiss();
				}
			}
		});
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);

		return this;
	}

    /**
     * 设置标题
     * @param title
     * @return
     */
	public DialogActionSheet setTitle(String title) {
		showTitle = true;
		llTitle.setVisibility(View.VISIBLE);
		txt_title.setText(title);
		return this;
	}

    /**
     * 设置标题大小
     * @param size
     * @return
     */
	public DialogActionSheet setTitleSize(float size){
	    txt_title.setTextSize(size);
	    return this;
    }

    /**
     * 设置标题颜色
     * @param mTitleColor
     * @return
     */
	public DialogActionSheet setTitleColor(int mTitleColor){
	    txt_title.setTextColor(mTitleColor);
	    return this;
    }

    /**
     * 设置取消按钮的文字
     * @param content
     * @return
     */
    public DialogActionSheet setCancelText(String content){
	    txt_cancel.setText(content);
	    return this;
    }

    /**
     * 设置取消按钮的文字颜色
     * @param color
     * @return
     */
    public DialogActionSheet setCancelColor(int color){
        txt_cancel.setTextColor(color);
        return this;
    }

	public DialogActionSheet setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public DialogActionSheet setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public DialogActionSheet setOnDismissListener(OnDialogDismissListener dismissListener){
		this.dismissListener = dismissListener;
		return this;
	}

	/**
	 * 
	 * @param strItem
	 *            条目名称
	 * @param color
	 *            条目字体颜色，设置null则默认蓝色
	 * @param listener
	 * @return
	 */
	public DialogActionSheet addSheetItem(String strItem, int color,
                                          OnSheetItemClickListener listener) {
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		sheetItemList.add(new SheetItem(strItem, color, listener));
		return this;
	}

	/** 设置条目布局 */
	private void setSheetItems() {
		if (sheetItemList == null || sheetItemList.size() <= 0) {
			return;
		}
        ActionSheetAdapter mActionSheetAdapter = new ActionSheetAdapter();
		sLayout_content.setAdapter(mActionSheetAdapter);
		sLayout_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SheetItem item = sheetItemList.get(position);
                if(item.itemClickListener != null){
                    item.itemClickListener.onClick(position);
                }
                dialog.dismiss();
            }
        });
	}

	public void show() {
		setSheetItems();
		dialog.show();
	}

	public interface OnSheetItemClickListener {
		void onClick(int which);
	}
	public interface OnDialogDismissListener{
		void dismiss();
	}

	private class ActionSheetAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return sheetItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return sheetItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if(convertView == null){
                convertView = View.inflate(context, R.layout.adapter_action_sheet, null);
                holder = new Holder();
                holder.tvContactValue = (TextView) convertView.findViewById(R.id.tvValue);
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }
            final SheetItem info = sheetItemList.get(position);
            holder.tvContactValue.setText(info.name);
            holder.tvContactValue.setTextSize(info.textSize);
            holder.tvContactValue.setTextColor(info.color);
            return convertView;
        }

        class Holder {
            public TextView tvContactValue;
        }
    }

	public class SheetItem {
		String name;
		OnSheetItemClickListener itemClickListener;
		int color;
		float textSize;

        public SheetItem(String name, int color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.textSize = 14;
            this.itemClickListener = itemClickListener;
        }

		public SheetItem(String name, int color,float textSize,
                         OnSheetItemClickListener itemClickListener) {
			this.name = name;
			this.color = color;
			this.textSize = textSize;
			this.itemClickListener = itemClickListener;
		}
	}

}
