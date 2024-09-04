package com.echo.datatag3.widget.bottomdialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;

import com.echo.datatag3.R;
import com.echo.datatag3.bean.enumeration.DataType;
import com.echo.datatag3.util.system.TimeUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImportFilesDialog extends BaseBottomDialog {
    private final Callback callback;
    private final Set<String> paths = new HashSet<>();

    private DataType dataType;
    private Spinner dataTypeSpinner;
    private TextView fileNumTextView;
    private AppCompatImageButton clearBtn;
    private Button chooseBtn;
    private SwitchCompat localCachingSwitch;
    private MaterialEditText fileTag;
    private Button confirmBtn;

    public ImportFilesDialog(FragmentManager fragmentManager, Callback callback) {
        super();
        setFragmentManager(fragmentManager);
        this.callback = callback;
    }

    public void refresh() {
        dataType = DataType.TEXT;
        localCachingSwitch.setChecked(false);
        paths.clear();
        fileTag.setText("");
    }

    public boolean isLocalCaching() {
        return localCachingSwitch.isChecked();
    }

    public String getFileTag() {
        return fileTag.getText()==null?"":fileTag.getText().toString();
    }

    public void addPaths(List<String> paths){
        this.paths.addAll(paths);
        fileNumTextView.setText(String.valueOf(this.paths.size()));
        if (!this.paths.isEmpty()){
            confirmBtn.setClickable(true);
            clearBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.bottom_dialog_import_dialog;
    }

    @Override
    protected String getFragmentTag() {
        return String.valueOf(TimeUtil.getCurrentTime());
    }

    @Override
    protected void bindView(View v) {
        getDialog().setCanceledOnTouchOutside(true);

        dataType = DataType.TEXT;
        dataTypeSpinner = v.findViewById(R.id.data_type_spinner);
        clearBtn = v.findViewById(R.id.clear_selected_btn);
        chooseBtn = v.findViewById(R.id.choose_file_btn);
        fileTag = v.findViewById(R.id.file_tag);
        localCachingSwitch = v.findViewById(R.id.local_caching_switch);
        fileNumTextView = v.findViewById(R.id.file_num);
        confirmBtn = v.findViewById(R.id.confirm_btn);

        dataTypeSpinner.setSelection(0);
        dataTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fileNumTextView.setText("");
                clearBtn.setVisibility(View.INVISIBLE);
                paths.clear();
                dataType = DataType.getByIndex(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        clearBtn.setOnClickListener(v1 -> {
            fileNumTextView.setText("暂未选择");
            clearBtn.setVisibility(View.INVISIBLE);
            confirmBtn.setClickable(false);
            paths.clear();
        });
        chooseBtn.setOnClickListener(v1 -> callback.onChoose(dataType));
        confirmBtn.setOnClickListener(v1 -> {
            getDialog().dismiss();
            callback.onConfirm(new ArrayList<>(paths));
        });
    }

    @Override
    public void dismiss() {

    }

    public interface Callback {
        void onConfirm(List<String> paths);

        void onChoose(DataType dataType);
    }
}
