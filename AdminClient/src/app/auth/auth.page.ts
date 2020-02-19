import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
// import { LoadingController, MenuController } from '@ionic/angular';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { AppState } from '../reducers';
import * as AuthActions from './store/auth.actions';
import { AuthState } from './store/auth.reducer';

const USERNAME_ALLOWED_REGEXP = /^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$/;

@Component({
  selector: 'app-auth',
  styleUrls: ['./auth.page.scss'],
  templateUrl: './auth.page.html'
})
export class AuthPage implements OnInit {

  /**
   * The authentication form
   */
  protected authForm: FormGroup;

  /**
   * Holds all the subscriptions that will need to be cleaned up when a view swaps
   */
  private subscriptions = new Subscription();
  //
  // /**
  //  * This is the loader that was created and currently exists on the page
  //  * This variable will be used to dismiss that existing loader
  //  */
  // private pageLoader: HTMLIonLoadingElement;

  constructor(
    public menuController: MenuController,
    public loadingController: LoadingController,
    private store: Store<AppState>,
    private snackBar: MatSnackBar
  ) {

  }

  async ionViewWillEnter(): Promise<void> {
    // Disable sideway scroll on log in page
    await this.menuController.enable(false);
    this.subscriptions.add(
      this.store.select('auth').subscribe((state: AuthState) => {
        if (state.errorMessage) {
          this.showMessage(state.errorMessage);
        }
        this.handleLoaderDisplay(state.loading);
      })
    );
  }

  ionViewWillLeave(): void {
    // Clean up all subs to avoid memory leak
    this.subscriptions.unsubscribe();

    // Ensure the loader is no longer visible as we are being redirected and that page will display if needed
    this.pageLoader.dismiss();
  }

  ngOnInit(): void {
    this.authForm = this.formInitialization();
  }

  /**
   * Submit form
   */
  protected onSubmit(): void {
    this.store.dispatch(AuthActions.loginAttempt({
      username: this.authForm.get('username').value as string,
      password: this.authForm.get('password').value as string
    }));
  }

  /**
   * Check if username is in the correct format
   */
  protected get usernameHasError(): boolean {
    return this.authForm.get('usernmae').invalid;
  }

  /**
   * Returns appropriate error message for username validation
   */
  protected get usernameErrorMessage(): string {
    const usernameCtrl = this.authForm.get('username');

    // Check if the username has been filled
    if(usernameCtrl.hasError('required')){
      return 'Username is required';
    }

    return '';
  }

  /**
   * Check if password is in correct format
   */
  protected get passwordHasError(): boolean {
    return this.authForm.get('password').invalid;
  }

  /**
   * Returns appropriate error message for password validation
   */
  protected get passwordErrorMessage(): string {
    const passwordCtrl = this.authForm.get('password');

    if(passwordCtrl.hasError('required')){
      return 'Password is required!';
    }

    return '';
  }

  protected isFormValid(): boolean {
    return this.authForm.valid;
  }

  /**
   * Displays a message message on the Auth Page as a little toast at the bottom
   * @param message The message to display
   * @param duration The duration for the message
   */
  private showMessage = (message: string, duration = 2000): void => {
    this.snackBar.open(message, undefined, {
      duration: 2000
    });
  };

  /**
   * Decides if page should display the loading spinner
   * @param shouldBeDisplayed If loader should be displayed
   */
  private handleLoaderDisplay = (shouldBeDisplayed: boolean): void => {
    if (!shouldBeDisplayed && this.pageLoader) {
      this.pageLoader.dismiss();

      return;
    }
    if (shouldBeDisplayed) {
      this.loadingController.create({
        message: 'Loading...'
      }).then(loader => {
        this.pageLoader = loader;
        this.pageLoader.present();
      });
    }
  };

  /**
   * Simply initializes the form to be used with default values and validators
   *
   * A big note for this class is that the Form state is not stored in the global store object state
   * The reason behind this is that a form state that has not been submitted is a very localized state and should NOT be shared
   * Between components thus does not belong in the global app store state, rather when a form is submitted then use the app store
   */
  private formInitialization = (): FormGroup =>
    new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
}
