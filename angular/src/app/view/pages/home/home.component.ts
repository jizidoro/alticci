import { CalculateAlticciUsecase } from '../../../core/usecases/alticci/calculate-alticci.usecase';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AlticciModel } from 'src/app/core/models/alticci.model';

@Component({
  templateUrl: 'home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  @ViewChild('resultDiv') resultDiv!: ElementRef;

  alticciForm = new FormGroup({
    alticciInput: new FormControl<number>(0, [
      Validators.required,
      Validators.pattern(/^-?(0|[1-9]\d*)?$/),
    ]),
  });

  alticciResult?: AlticciModel;

  getNewHeight() {
    this.resultDiv.nativeElement.style.height = 'auto';
    this.resultDiv.nativeElement.style.height = `${this.resultDiv.nativeElement.scrollHeight}px`;
  }

  constructor(private calculateAlticci: CalculateAlticciUsecase) {}

  onSubmit() {
    this.calculateAlticci.execute(this.alticciForm.value.alticciInput!).subscribe((response) => {
      this.alticciResult = response.data;
      this.getNewHeight();
    });
  }
}
