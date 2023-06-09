package com.example.siculi.AdminFragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.siculi.LoginActivty;
import com.example.siculi.Model.AdminModel;
import com.example.siculi.Model.ResponseModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProfileFragment extends Fragment {
    ImageView ivProfile;
    EditText etEmail;
    TextView tvNama, tvEmail, etStatus;
    EditText etNip, etJabatan, etNama, etPassword;
    Button btnEdit, btnSimpan, btnSimpanProfile, btnLogOut;
    String userId;
    SharedPreferences sharedPreferences;
    AdminInterface adminInterface;
    private File file;
    SharedPreferences.Editor editor;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_profil, container, false);
        ivProfile = view.findViewById(R.id.ivProfile);
        tvNama = view.findViewById(R.id.tvName);
        btnSimpanProfile = view.findViewById(R.id.btnSimpanProfile);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        etPassword = view.findViewById(R.id.etPassword);
        etNama = view.findViewById(R.id.etNama);
        etNip = view.findViewById(R.id.etNip);
        etJabatan = view.findViewById(R.id.etJabatan);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        etStatus = view.findViewById(R.id.etStatus);
        btnEdit = view.findViewById(R.id.btnUbah);
        etEmail = view.findViewById(R.id.etEmail);
        adminInterface = DataApi.getClient().create(AdminInterface.class);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
        editor = sharedPreferences.edit();

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                btnSimpanProfile.setVisibility(View.VISIBLE);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(getContext(), LoginActivty.class));
                getActivity().finish();
            }
        });




        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               btnSimpan.setVisibility(View.VISIBLE);
               etEmail.setEnabled(true);
               etNip.setEnabled(true);
               etPassword.setEnabled(true);
               etNip.requestFocus();
               etNama.setEnabled(true);
               btnEdit.setVisibility(View.GONE);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMyProfile();
            }
        });

        btnSimpanProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhotoProfile();
            }
        });

        getMyProfile();






        return view;
    }

    private void getMyProfile() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getMyProfile(userId).enqueue(new Callback<AdminModel>() {
            @Override
            public void onResponse(Call<AdminModel> call, Response<AdminModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvNama.setText(response.body().getNama());
                    tvEmail.setText(response.body().getEmail());
                    etEmail.setText(response.body().getEmail());
                    etNip.setText(response.body().getNik());
                    etPassword.setText(response.body().getPassword());
                    etJabatan.setText(response.body().getJabatan());
                    etStatus.setText(response.body().getStatus());
                    etNama.setText(response.body().getNama());

                    Glide.with(getContext())
                            .load(response.body().getFoto())
                            .override(250, 250)
                            .skipMemoryCache(true)
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .centerCrop()
                            .fitCenter()
                            .into(ivProfile);
                    pd.dismiss();

                }else {
                    pd.dismiss();
                    Toasty.error(getContext(), "Gagal memuat data karyawan", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminModel> call, Throwable t) {
                pd.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });


    }

    private void editMyProfile() {
        if (etNama.getText().toString().isEmpty()) {
            etNama.setError("Nama tidak boleh kosong");
            etNama.requestFocus();
            return;
        }else if (etNip.getText().toString().isEmpty()) {
            etNip.setError("NIP tidak boleh kosong");
            etNip.requestFocus();
            return;
        }else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Email tidak boleh kosong");
            etEmail.requestFocus();
            return;
        }else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password tidak boleh kosong");
            etPassword.requestFocus();
            return;
        }else {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Loading").setMessage("Menyimpan perubahan...").setCancelable(false);
            AlertDialog pd = alert.create();
            pd.show();

            HashMap map = new HashMap();
            map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));
            map.put("email", RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString()));
            map.put("nama", RequestBody.create(MediaType.parse("text/plain"), etNama.getText().toString()));
            map.put("nik", RequestBody.create(MediaType.parse("text/plain"), etNip.getText().toString()));
            map.put("email", RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString()));
            map.put("password", RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString()));

            adminInterface.updateMyProfile(map).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call <ResponseModel>call, Response<ResponseModel> response) {
                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                        btnSimpan.setVisibility(View.GONE);
                        btnEdit.setVisibility(View.VISIBLE);
                        etNip.setEnabled(false);
                        etNama.setEnabled(false);
                        etPassword.setEnabled(false);
                        Toasty.success(getContext(), "Berhasil mengubah profil", Toasty.LENGTH_SHORT).show();
                        getMyProfile();
                        pd.dismiss();
                    }else {
                        Toasty.error(getContext(), response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                 }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });
        }
    }

    private void editPhotoProfile() {

            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Loading").setMessage("Menyimpan perubahan...").setCancelable(false);
            AlertDialog pd = alert.create();
            pd.show();

            HashMap map = new HashMap();
            map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("foto", file.getName(), requestBody);

            adminInterface.editPhotoProfile(map, image).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call <ResponseModel>call, Response<ResponseModel> response) {
                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                       btnSimpanProfile.setVisibility(View.GONE);
                        Toasty.success(getContext(), "Berhasil mengubah foto profil", Toasty.LENGTH_SHORT).show();
                        getMyProfile();
                        pd.dismiss();
                    }else {
                        Toasty.error(getContext(), response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                file = new File(pdfPath);
                ivProfile.setImageURI(uri);
            }
        }
    }


    public String getRealPathFromUri(Uri uri) {
        String filePath = "";
        if (getContext().getContentResolver() != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                File file = new File(getContext().getCacheDir(), getFileName(uri));
                writeFile(inputStream, file);
                filePath = file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void writeFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}