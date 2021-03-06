package chamelion.chamelophone.com.usermanagementportal.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chamelion.chamelophone.com.usermanagementportal.R;
import chamelion.chamelophone.com.usermanagementportal.presentation.LoginPresenter;
import chamelion.chamelophone.com.usermanagementportal.ui.di.MyApplication;
import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.mView {

  @Inject LoginPresenter loginPresenter;

  @BindView(R.id.LoginEmail) EditText email;
  @BindView(R.id.LoginPassword) EditText password;

  private String mPassword;
  private String mEmail;

  ProgressDialog progressDialog;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ((MyApplication) getApplication())
        .getMyComponent().inject(LoginActivity.this);
    ButterKnife.bind(this);

    progressDialog=new ProgressDialog(this);
    progressDialog.setMessage(getString(R.string.pleasewait));

    loginPresenter.setView(this);
  }

  @OnClick(R.id.LoginButton)
  public void login() {
    progressDialog.show();
    mEmail = email.getText().toString().trim();
    mPassword = password.getText().toString().trim();
    loginPresenter.authUser(mEmail, mPassword);
  }

  @OnClick(R.id.RegisterBtn)
  public void register(){
    startActivity(new Intent(this,RegisterActivity.class));
  }

  @Override public void sucess() {
    progressDialog.dismiss();
    startActivity(new Intent(this,DashBoard.class));
    Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
    finish();
  }

  @Override public void failed(String message) {
    progressDialog.dismiss();
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }


}
