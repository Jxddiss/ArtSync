import { Component, Renderer2, ElementRef } from '@angular/core';

@Component({
  selector: 'app-general-view',
  templateUrl: './general-view.component.html',
  styleUrl: './general-view.component.css'
})
export class GeneralViewComponent {
  constructor(private renderer: Renderer2, private el: ElementRef) { }

  selectSection(event: Event): void {
    const sections = this.el.nativeElement.querySelectorAll('.section');
    sections.forEach((section: HTMLElement) => {
      this.renderer.removeClass(section, 'selected');
    });
    this.renderer.addClass(event.target as HTMLElement, 'selected');
  }
}
